package gent.timdemey.cards.base.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.processing.CLT_JoinServer;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.SRV_AddPlayer;
import gent.timdemey.cards.base.processing.ServerVisitor;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.utils.StringUtils;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ServerRunner {

    //
    public static final Charset HANDSHAKE_CHARSET = Charset.forName("utf-8");
    public static final byte[] BYTES_HANDSHAKE_INIT = "CARDS_HANDHAKE_INIT".getBytes(HANDSHAKE_CHARSET);
    public static final byte[] BYTES_HANDSHAKE_ACK = "CARDS_HANDHAKE_ACK".getBytes(HANDSHAKE_CHARSET);

    private static final Logger logger = LogManager.getLogger("ServerRunner", new StringFormatterMessageFactory());

    private final String id;
    private final String name;
    private final int port;

    private final byte[] bytes_id;
    private final byte[] bytes_name;

    private volatile boolean used = false;

    private Thread udpDiscoveryThr;
    private Thread tcpGameThr;
    private volatile DatagramSocket discSocket;
    private volatile ServerSocket tcpSocket;

    public static void main(String[] args) {
        try {
            new ServerRunner("ABCD", "A good server", 9999).start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ServerRunner(String id, String name, int port_handshake) {
        this.id = id;
        this.name = name;
        this.port = port_handshake;
        this.bytes_id = id.getBytes(HANDSHAKE_CHARSET);
        this.bytes_name = name.getBytes(HANDSHAKE_CHARSET);
        this.udpDiscoveryThr = new Thread(() -> listen_loop(), "UDP Server Discovery Socket Listener");
        this.tcpGameThr = new Thread(() -> connect_loop(), "TCP Server Handshake Listener");
    }

    public void start() throws IOException {
        if (used) {
            throw new IllegalStateException(
                    "This server has already run or is currently running, create new instance instead");
        }
        used = true;

        // create a game
        State.INSTANCE.addServer(new Server(id, id, name));
        Processor.INSTANCE.addVisitor(new ServerVisitor(new SolitaireRules()));

        // create a socket that accept connections (TCP)
        tcpSocket = new ServerSocket(port);

        tcpGameThr.start();

        // create a UDP socket for client to find this server on the LAN
        discSocket = new DatagramSocket(ConnectionConstants.PORT_UDP_DISCOVERY, InetAddress.getByName("0.0.0.0"));
        discSocket.setBroadcast(true);
        udpDiscoveryThr.start();
    }

    public void stop() {
        if (!used) {
            throw new IllegalStateException("Not started.");
        }

        discSocket.close();
        try {
            tcpSocket.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            udpDiscoveryThr.join();
            tcpGameThr.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            udpDiscoveryThr = null;
            tcpGameThr = null;
            discSocket = null;
            tcpSocket = null;
        }
    }

    /**
     * Parts taken from
     */
    private void listen_loop() {
        try {
            while (true) {
                try {
                    // Receive a packet
                    byte[] recvBuf = new byte[BYTES_HANDSHAKE_INIT.length];
                    DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                    discSocket.receive(packet);

                    // See if the packet holds the right command (message)
                    byte[] data = packet.getData();
                    if (!Arrays.equals(data, BYTES_HANDSHAKE_INIT)) {
                        continue;
                    }

                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(bs);
                    dos.write(BYTES_HANDSHAKE_ACK);
                    dos.writeInt(bytes_id.length);
                    dos.write(bytes_id);
                    dos.writeInt(bytes_name.length);
                    dos.write(bytes_name);
                    dos.writeInt(port);
                    byte[] senddata = bs.toByteArray();

                    bs.close();
                    dos.close();

                    // Send a response
                    InetAddress to_ip = packet.getAddress();
                    int to_port = packet.getPort();
                    DatagramPacket sendPacket = new DatagramPacket(senddata, senddata.length, to_ip, to_port);
                    discSocket.send(sendPacket);

                    logger.info("Discovery packet received from: %s", packet.getAddress().getHostAddress());

                } catch (SocketException ex) {
                    if (!discSocket.isClosed()) {
                        ex.printStackTrace();
                    } else {
                        throw ex;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect_loop() {
        try {
            while (true) {
                Socket clsocket = tcpSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(clsocket.getInputStream()));
                String line = br.readLine();
                Command recvcmd = Connection.gson.fromJson(line, B_Message.class).getCommand();
                if (recvcmd instanceof CLT_JoinServer) {
                    CLT_JoinServer joincmd = (CLT_JoinServer) recvcmd;

                    Server srv = State.INSTANCE.getServer(joincmd.server_id);
                    String generated_id;
                    do {
                        generated_id = StringUtils.getRandomString(4);
                    } while (srv.isPlayer(generated_id));
                    String assigned_id = generated_id;
                    
                    Connection conn = ConnectionManager.newConnection(clsocket);
                    conn.addMessageListener(msg -> Processor.INSTANCE.process(msg.getCommand()));
                    ConnectionManager.startConnection(conn, assigned_id);

                    new SRV_AcceptConnect(assigned_id, joincmd.server_id, srv.getName()).unicast(assigned_id);
                    srv.getPlayers().forEach(p -> {
                        new SRV_AddPlayer(p.getId(), joincmd.player_name).setSourceID(joincmd.server_id)
                                .unicast(assigned_id);
                    });
                    srv.addPlayer(assigned_id, joincmd.player_name);
                    new SRV_AddPlayer(assigned_id, joincmd.player_name).setSourceID(joincmd.server_id).broadcast();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
