package gent.timdemey.cards.base.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gent.timdemey.cards.base.beans.B_Message;

public class ConnectionManager {

    public static final String INTERNAL_ID = "INTERNAL";
    public static final String BROADCAST_ID = "BROADCAST";
    
    private static final Map<String, Connection> connections = new HashMap<>();

    private ConnectionManager() {

    }

    public static Connection getConnection(String id) {
        return connections.get(id);
    }

    public static Connection dropConnection(String id) {
        Connection c = connections.remove(id);
        c.stop();
        return c;
    }

    public static void dropAllConnections() {
        new ArrayList<>(connections.keySet()).stream().forEach(ConnectionManager::dropConnection);
    }

    public static void write(B_Message msg) {
        if (BROADCAST_ID.equals(msg.getCommand().getDestinationID())) {
            connections.values().stream().forEach(conn -> conn.write(msg));
        } else {
            connections.get(msg.getCommand().getDestinationID()).write(msg);
        }
    }

    public static Connection newConnection(Socket s) throws IOException {
        return new Connection(s);
    }
    

    public static void startConnection(Connection conn, String id) {
        connections.put(id, conn);
        conn.start(id);
    }

    public static boolean isConnection(String id) {
        return connections.containsKey(id);
    }
}
