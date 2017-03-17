package gent.timdemey.cards.base;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

public class ServerRunner {

    //
    public static final Charset HANDSHAKE_CHARSET = Charset.forName("utf-8");
    public static final byte[] BYTES_HANDSHAKE_INIT = "CARDS_HANDHAKE_INIT".getBytes(HANDSHAKE_CHARSET);
    public static final byte[] BYTES_HANDSHAKE_ACK = "CARDS_HANDHAKE_ACK".getBytes(HANDSHAKE_CHARSET);

    private static final Logger logger = LogManager.getLogger("ServerRunner", new StringFormatterMessageFactory());

    private final byte[] bytes_id;
    private Thread thread;
    private volatile DatagramSocket socket;

    public ServerRunner(String id) {
        this.bytes_id = id.getBytes(HANDSHAKE_CHARSET);

        this.thread = new Thread(() -> listen_loop(), "UDP Server Discovery Socket Listener");
    }

    public void start(int port) throws IOException {
        if (thread == null) {
            throw new IllegalStateException("Has already run, create new instance instead");
        }
        socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));

        socket.setBroadcast(true);
        thread.start();
    }

    public void stop() {
        if (thread == null) {
            throw new IllegalStateException("Not started.");
        }

        socket.close();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            thread = null;
            socket = null;
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
                    byte[] recvBuf = new byte[100];
                    DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                    socket.receive(packet);

                    // See if the packet holds the right command (message)
                    byte[] data = packet.getData();
                    if (!Arrays.equals(data, BYTES_HANDSHAKE_INIT)) {
                        continue;
                    }

                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(bs);
                    dos.write(BYTES_HANDSHAKE_ACK);
                    dos.write(bytes_id.length);
                    dos.write(bytes_id);
                    byte[] senddata = bs.toByteArray();
                    
                    bs.close();
                    dos.close();

                    // Send a response
                    InetAddress to_ip = packet.getAddress();
                    int to_port = packet.getPort();
                    DatagramPacket sendPacket = new DatagramPacket(senddata, senddata.length, to_ip, to_port);
                    socket.send(sendPacket);

                    logger.info("Discovery packet received from: %s", packet.getAddress().getHostAddress());

                } catch (SocketException ex) {
                    if (!socket.isClosed()) {
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
}
