package gent.timdemey.cards.base.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import gent.timdemey.cards.base.beans.B_Message;

public class Messenger {

    private static final String BOOTSTRAP_CONNECTION_NAME = "TEMP-CONNECTION-NAME";
    
    private final Map<String, Connection> connections;

    
    public Messenger() throws IOException {
        this.connections = new HashMap<>();
    }

    public Connection getConnection(String id) {
        return connections.get(id);
    }

    public Connection removeConnection(String id) {
        Connection c = connections.remove(id);
        c.stop();
        return c;
    }

    public void write(B_Message msg) {
        if ("broadcast".equals(msg.getCommand().getDestination())) {
            connections.values().stream().forEach(conn -> conn.write(msg));
        } else {
            connections.get(msg.getCommand().getDestination()).write(msg);
        }
    }

    public void addNewConnection(Connection c) throws IOException {
        if (connections.get(BOOTSTRAP_CONNECTION_NAME) != null){
            throw new IllegalStateException("You cannot be bootstrapping connections with 2 parties at once!");
        }
        connections.put(BOOTSTRAP_CONNECTION_NAME, c);
        c.addConnectionListener(conn -> connections.remove(conn));
    }
    
    public void setNewConnectionId (String id){
        Connection c = connections.remove(BOOTSTRAP_CONNECTION_NAME);
        connections.put(id, c);
        c.setName(id);
    }

    public boolean isConnection(String name) {
        return connections.containsKey(name);
    }
}
