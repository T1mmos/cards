package gent.timdemey.cards.base.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Connection {

    private final String id;
    private final Set<LineListener> lineListeners;
    private final Set<ConnectionListener> connListeners;
    private final InetAddress inetAddr;
    
    private Writer writer;
    private BufferedReader reader;
    private Thread readThr = null;
    private Socket socket;

    Connection(String id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.lineListeners = ConcurrentHashMap.newKeySet();
        this.connListeners = ConcurrentHashMap.newKeySet();
        
        this.inetAddr = socket.getInetAddress();
    }

    void start() {
        readThr = new Thread(() -> listen(), "Listener :: " + id);
        readThr.start();
    }

    void stop() {
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
        lineListeners.clear();
        connListeners.clear();
    }

    void write(String msg) {
        String line = msg.endsWith("\n") ? msg : (msg + "\n");
        try {
            writer.write(line);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void addLineListener(LineListener listener) {
        lineListeners.add(listener);
    }

    void addConnectionListener(ConnectionListener listener) {
        connListeners.add(listener);
    }

    private void listen() {
        try {
            while (true) {
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    break;
                }
                String line = reader.readLine();
                lineListeners.stream().forEach(l -> l.onReceive(line));
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
            connListeners.forEach(c -> c.onConnectionLost(this));
        }
    }
    
    public InetAddress getInetAddress (){
        return inetAddr;
    }
    
    public String getId(){
        return id;
    }
}
