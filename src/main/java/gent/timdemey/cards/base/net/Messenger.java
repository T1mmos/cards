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

    public void addMessageListener(String id, MessageListener listener) {
        connections.get(id).addLineListener(s -> {
            B_Message msg = gson.fromJson(s, B_Message.class);
            listener.onReceive(msg);
        });
    }

    public void addConnectionListener(String id, ConnectionListener listener) {
        connections.get(id).addConnectionListener(listener);
    }

    public void write(B_Message msg) {
        String line = gson.toJson(msg);
        if ("broadcast".equals(msg.getCommand().getDestination())) {
            connections.values().stream().forEach(conn -> conn.write(line));
        } else {
            connections.get(msg.getCommand().getDestination()).write(line);
        }
    }

    public void addConnection(String name, Socket socket) throws IOException {
        Connection c = new Connection(name, socket);
        connections.put(name, c);
        addConnectionListener(name, id -> connections.remove(id));
    }
    
    public void startConnection (String name){
        connections.get(name).start();
    }
    
    public void stopConnection (String name){
        connections.get(name).stop();
    }

    public boolean isConnection(String name) {
        return connections.containsKey(name);
    }
}
