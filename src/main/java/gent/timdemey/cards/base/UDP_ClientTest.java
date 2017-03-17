package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class UDP_ClientTest implements Runnable {
    DatagramSocket c;

    public static void main(String[] args) {
        new Thread(new UDP_ClientTest()).start();
    }
    
    @Override
    public void run() {
        // Find the server using UDP broadcast
        try {
            // Open a random port to send the package
            c = new DatagramSocket();
            c.setBroadcast(true);

            

            // Try the 255.255.255.255 first
          /*  try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        InetAddress.getByName("255.255.255.255"), 8888);
                c.send(sendPacket);
                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
            }*/

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
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        c.send(sendPacket);
                    } catch (Exception e) {
                    }
                }
            }

            // Wait for a response
            byte[] recvBuf = new byte[100];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);
            
            

            // Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
                // DO SOMETHING WITH THE SERVER'S IP (for example, store it in
                // your controller)
                System.out.println("SHOW: " + receivePacket.getAddress().getHostAddress());
            }

            // Close the port!
            c.close();
        } catch (IOException ex) {
        }
    }
}
