package gent.timdemey.cards.base.net;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import gent.timdemey.cards.base.processing.SRV_InitPlayer;
import gent.timdemey.cards.base.processing.SRV_RemovePlayer;

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
