package gent.timdemey.cards.base.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPinger {

    private static final Logger logger = LogManager.getLogger("ServerPinger");
    private final Set<PingbackHandler> pingbacks;
    private volatile DatagramSocket socket;
    private volatile Thread pingThr;
    private volatile Thread timerThr;

    public ServerPinger() throws SocketException {
        this.pingbacks = Collections.synchronizedSet(new HashSet<>());
        this.socket = new DatagramSocket();
        this.pingThr = new Thread(() -> listen(), "Server Pinger Socket Listener");
        this.timerThr = new Thread(() -> startTiming(), "Server Pinger Timeout Timer");
        
        socket.setBroadcast(true);
    }

    public void ping() {
        pingThr.start();
        timerThr.start();
    }
    
    public void addPingbackHandler (PingbackHandler handler){
        pingbacks.add(handler);
    }
    public void removePingbackHandler (PingbackHandler handler){
        pingbacks.remove(handler);
    }
    private void startTiming() {
        try {
            Thread.sleep(3000);
            socket.close();
            pingThr.join();
            pingThr = null;
            timerThr = null;
        } catch (InterruptedException e) {
            // nobody interrupts this private thread.
        }
    }

    private void listen() {
        try {

            // Try the 255.255.255.255 first
            /*
             * try { DatagramPacket sendPacket = new DatagramPacket(sendData,
             * sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
             * c.send(sendPacket); System.out.println(getClass().getName() +
             * ">>> Request packet sent to: 255.255.255.255 (DEFAULT)"); } catch
             * (Exception e) { }
             */

            // Broadcast the message over all the network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback
                              // interface
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    // Send the broadcast package!
                    try {
                        byte[] sendData = ServerRunner.BYTES_HANDSHAKE_INIT;
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, ConnectionConstants.PORT_UDP_DISCOVERY);
                        System.out.println("Sending datagram: " + sendPacket);
                        socket.send(sendPacket);
                    } catch (Exception e) {
                    }
                }
            }

            // Wait for a response
            while (true) {
                byte[] recvBuf = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(receivePacket);

                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(recvBuf));

                // should start with handshake header.
                byte[] handshake_ack = new byte[ServerRunner.BYTES_HANDSHAKE_ACK.length];
                dis.read(handshake_ack, 0, handshake_ack.length);
                if (!Arrays.equals(handshake_ack, ServerRunner.BYTES_HANDSHAKE_ACK)) {
                    continue;
                }

                // next comes the server ID.
                int handshake_id_len = dis.readInt();
                if (handshake_id_len <= 0) {
                    continue;
                }
                byte[] handshake_id = new byte[handshake_id_len];
                dis.read(handshake_id, 0, handshake_id.length);
                String id = new String(handshake_id, ServerRunner.HANDSHAKE_CHARSET);

                // and server name
                int handshake_name_len = dis.readInt();
                if (handshake_name_len <= 0) {
                    continue;
                }
                byte[] handshake_name = new byte[handshake_name_len];
                dis.read(handshake_name, 0, handshake_name.length);
                String name = new String(handshake_name, ServerRunner.HANDSHAKE_CHARSET);
                
                // and game port to connect to
                int port = dis.readInt();

                pingbacks.forEach(p -> p.onPingback(id, name, receivePacket.getAddress(), port));
            }

        } catch (IOException ex) {
            if (!socket.isClosed()){
                ex.printStackTrace();
            } else {
                logger.info("Pinging stopped, closed socket");
            }
        }
    }
}
