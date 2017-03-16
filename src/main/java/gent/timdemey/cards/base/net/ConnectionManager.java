package gent.timdemey.cards.base.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import gent.timdemey.cards.base.beans.B_Message;

public class ConnectionManager {

    public static final String BROADCAST_ID = "BROADCAST";
    private static final String BOOTING_ID = "BOOTING";
    private static final Map<String, Connection> established = new HashMap<>();
    private static final List<Connection> booting= new ArrayList<>();

    private ConnectionManager(){
        
    }
    
    public static Connection getConnection(String id) {
        return established.get(id);
    }

    public static Connection removeConnection(String id) {
        Connection c = established.remove(id);
        c.stop();
        return c;
    }

    public static void write(B_Message msg) {
        if (BROADCAST_ID.equals(msg.getCommand().getDestinationID())) {
            established.values().stream().filter(conn -> !BOOTING_ID.equals(conn.getId())).forEach(conn -> conn.write(msg));
        } else {
            Connection unicast_conn = established.get(msg.getCommand().getDestinationID());
            if (BOOTING_ID.equals(unicast_conn.getId())){
                throw new IllegalStateException("Cannot unicast to a booting connection!");
            }
            unicast_conn.write(msg);
        }
    }

    /**
     * Adds a connection to this pool which cannot yet be identified by an id
     * assigned by the server, because there was no negotiation yet. The
     * connection is added to a list of "booting connections" who can be
     * identified by the network address and port their underlying sockets are
     * connected to. Such connections should be migrated to the pool of
     * established connections whenever the remote party's ID was sent to the
     * local party.
     * 
     * @param c
     * @throws IOException
     */
    public static Connection newBootingConnection(Socket s) throws IOException {
        Connection c = new Connection(s, BOOTING_ID);
        booting.add(c);
        c.addConnectionListener(conn -> booting.remove(conn));
        c.startImpl();
        return c;
    }
    
    public static Connection newEstablishedConnection(Socket s, String id) throws IOException {
        Connection c = newBootingConnection(s);
        establish(s.getInetAddress(), s.getPort(), id);
        return c;
    }
    
    static void startConnection (Connection conn){
        conn.startImpl();
    }
    
    static void destroyConnection (Connection conn){
        conn.stopImpl();
    }

    /**
     * Migrates a booting connection to the pool of established connections. The
     * connection can now be identified by the given ID (instead of the
     * combination of given IP and port) and one can start writing to the connection as well.
     * 
     * @param id
     */
    public static  void establish(InetAddress remote_ip, int remote_port, String assigned_id) {
        Predicate<Connection> pred = c -> c.getInetAddress().equals(remote_ip) && c.getPort() == remote_port;
        Connection conn = booting.stream().filter(pred).findFirst().get();
        booting.remove(conn);
        established.put(assigned_id, conn);
        conn.setId(assigned_id);
    }

    public static boolean isEstablishedConnection(String id) {
        return established.containsKey(id);
    }
}
