package gent.timdemey.cards.base.net;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.processing.CLT_AbortPickUp;
import gent.timdemey.cards.base.processing.CLT_InitPlayer;
import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.processing.CLT_RequestLobbyList;
import gent.timdemey.cards.base.processing.CLT_TransferCommand;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.SRV_InitPlayer;
import gent.timdemey.cards.base.processing.SRV_RemovePlayer;

public class Messenger {

    private static final Gson gson;
    public static final RuntimeTypeAdapterFactory<Command> GSON_COMMAND_ADAPTER;

    static {
        GSON_COMMAND_ADAPTER = RuntimeTypeAdapterFactory.of(Command.class);

        GSON_COMMAND_ADAPTER.registerSubtype(CLT_PickUp.class, "PickUp");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_PutDown.class, "PutDown");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_AbortPickUp.class, "PickUpAbort");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_TransferCommand.class, "Transfer");
        GSON_COMMAND_ADAPTER.registerSubtype(SRV_AcceptConnect.class, "AcceptConnect");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_RequestLobbyList.class, "RequestLobbyList");
        GSON_COMMAND_ADAPTER.registerSubtype(CLT_InitPlayer.class, "InitPlayer@Server");
        GSON_COMMAND_ADAPTER.registerSubtype(SRV_InitPlayer.class, "InitPlayer@Client");
        GSON_COMMAND_ADAPTER.registerSubtype(SRV_RemovePlayer.class, "RemovePlayer");

        gson = new GsonBuilder().registerTypeAdapterFactory(GSON_COMMAND_ADAPTER).create();
    }

    public static class Connection {

        private final String name;
        private final Set<MessageListener> msgListeners;
        private final Set<ConnectionListener> connListeners;
        private Writer writer;
        private BufferedReader reader;
        private Thread readThr = null;
        private Socket socket;

        private Connection(String name, Socket socket) throws IOException {
            this.name = name;
            this.socket = socket;
            this.writer = new PrintWriter(socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.msgListeners = ConcurrentHashMap.newKeySet();
            this.connListeners = ConcurrentHashMap.newKeySet();
        }

        private void start() {
            readThr = new Thread(() -> listen(), "Listener :: " + name);
            readThr.start();
        }

        private void stop() {
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
        }

        private void write(B_Message msg) {
            String json = gson.toJson(msg) + "\n";
            try {
                writer.write(json);
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        private void listen() {
            try {
                while (true) {
                    if (Thread.interrupted()) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    String line = reader.readLine();
                    B_Message msg = gson.fromJson(line, B_Message.class);
                    msgListeners.stream().forEach(l -> l.onReceive(msg));
                }
            } catch (IOException e) {
                socket = null;
                readThr = null;
                try {
                    reader.close();
                } catch (IOException e2){
                    System.out.println("Failed to close reader for connection to: " + name);
                }
                try {
                    writer.close();
                } catch (IOException e2){
                    System.out.println("Failed to close writer for connection to: " + name);
                }
                connListeners.forEach(c -> c.onConnectionLost(name));
            }
        }
    }

    private final Map<String, Connection> connections;

    public Messenger() throws IOException {
        this.connections = new HashMap<>();
    }

    public void addMessageListener(String id, MessageListener listener) {
        connections.get(id).msgListeners.add(listener);
    }

    public void addConnectionListener(String id, ConnectionListener listener) {
        connections.get(id).connListeners.add(listener);
    }

    public void write(B_Message msg) {
        if ("broadcast".equals(msg.getCommand().getDestination())) {
            connections.values().stream().forEach(conn -> conn.write(msg));
        } else {
            connections.get(msg.getCommand().getDestination()).write(msg);
        }
    }

    public void addConnection(String name, Socket socket) throws IOException {
        Connection c = new Connection(name, socket);
        connections.put(name, c);
        addConnectionListener(name, id -> connections.remove(id));
        c.start();
    }

    public boolean isConnection(String name) {
        return connections.containsKey(name);
    }

    public void removeConnection(String name) {
        Connection c = connections.remove(name);
        c.stop();
    }
}
