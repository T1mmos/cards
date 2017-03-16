package gent.timdemey.cards.base.net;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.processing.CLT_AbortPickUp;
import gent.timdemey.cards.base.processing.CLT_InitPlayer;
import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.processing.CLT_RequestGameList;
import gent.timdemey.cards.base.processing.CLT_TransferCommand;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.SRV_AddPlayer;
import gent.timdemey.cards.base.processing.SRV_RemovePlayer;

public class Connection {

    public static final RuntimeTypeAdapterFactory<Command> GSON_COMMAND_ADAPTER;

    private static final Logger logger = LogManager.getLogger("Connection", new StringFormatterMessageFactory());
    private static final Gson gson;

    static {
        // all supported commands for serialization
        //@formatter:off
        List<Class<? extends Command>> commands = Arrays.asList(
            CLT_PickUp.class ,
            CLT_PutDown.class,
            CLT_AbortPickUp.class,
            CLT_TransferCommand.class,
            SRV_AcceptConnect.class,
            CLT_RequestGameList.class,
            CLT_InitPlayer.class,
            SRV_AddPlayer.class,
            SRV_RemovePlayer.class
        );
        //@formatter:on      
        GSON_COMMAND_ADAPTER = RuntimeTypeAdapterFactory.of(Command.class);
        commands.stream().forEach(GSON_COMMAND_ADAPTER::registerSubtype);
        gson = new GsonBuilder().registerTypeAdapterFactory(GSON_COMMAND_ADAPTER).create();
    }

    private final Set<MessageListener> msgListeners;
    private final Set<ConnectionListener> connListeners;
    private final InetAddress inetAddr;
    private final int port;

    private Writer writer;
    private BufferedReader reader;
    private Thread readThr = null;
    private Socket socket;
    private String id;

    Connection(Socket socket, String initial_id) throws IOException {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.connListeners = ConcurrentHashMap.newKeySet();
        this.msgListeners = ConcurrentHashMap.newKeySet();
        this.inetAddr = socket.getInetAddress();
        this.port = socket.getPort();
        this.id = initial_id;
        this.readThr = new Thread(() -> listen(), "Socket == " + inetAddr.getHostAddress() + ":" + port);
    }

    void setId(String id) {
        this.id = checkNotNull(id);
        this.readThr.setName(toString());
        logger.info("Connection to %s:%s was given the id %s", inetAddr, port, id);
    }

    public void start() {
        ConnectionManager.startConnection(this);
    }

    public void stop() {
        ConnectionManager.destroyConnection(this);
    }

    void startImpl() {
        readThr.start();
    }

    void stopImpl() {
        readThr.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            readThr.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        connListeners.clear();
    }

    void write(B_Message msg) {
        String line = gson.toJson(msg);
        String crlfline = line.endsWith("\n") ? line : (line + "\n");
        try {
            writer.write(crlfline);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addMessageListener(MessageListener listener) {
        msgListeners.add(listener);
    }

    public void removeMessageListeners() {
        msgListeners.clear();
    }

    public void addConnectionListener(ConnectionListener listener) {
        connListeners.add(listener);
    }

    void listen() {
        logger.info("Started listening on connection to %s:%s", inetAddr, port);
        try {
            while (true) {
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    break;
                }
                String line = reader.readLine();
                B_Message msg = gson.fromJson(line, B_Message.class);
                msg.getCommand().setSourceIP(socket.getInetAddress());
                msg.getCommand().setSourcePort(socket.getPort());

                msgListeners.stream().forEach(l -> l.onReceive(msg));
            }
        } catch (IOException e) {
            socket = null;
            readThr = null;
            try {
                reader.close();
            } catch (IOException e2) {
                System.out.println("Failed to close reader for connection to: " + id);
            }
            try {
                writer.close();
            } catch (IOException e2) {
                System.out.println("Failed to close writer for connection to: " + id);
            }
        } finally {
            logger.info("Stopped listening on connection to %s:%s (id=%s)", inetAddr, port, id);
            connListeners.forEach(c -> c.onConnectionLost(this));
        }
    }

    public InetAddress getInetAddress() {
        return inetAddr;
    }

    public int getPort() {
        return port;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Connection @ " + getId();
    }
}
