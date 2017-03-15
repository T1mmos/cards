package gent.timdemey.cards.base.net;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import gent.timdemey.cards.base.beans.B_Message;

public class ConnectionManager {

    private final Map<String, Connection> established;
    private final List<Connection> booting;

    public ConnectionManager() throws IOException {
        this.established = new HashMap<>();
        this.booting = new ArrayList<>();
    }

    public Connection getConnection(String id) {
        return established.get(id);
    }

    public Connection removeConnection(String id) {
        Connection c = established.remove(id);
        c.stop();
        return c;
    }

    public void write(B_Message msg) {
        if ("broadcast".equals(msg.getCommand().getDestinationID())) {
            established.values().stream().forEach(conn -> conn.write(msg));
        } else {
            established.get(msg.getCommand().getDestinationID()).write(msg);
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
    public void addBootingConnection(Connection c) throws IOException {
        booting.add(c);
        c.addConnectionListener(conn -> booting.remove(conn));
    }

    /**
     * Migrates a booting connection to the pool of established connections. The
     * connection can now be identified by the given ID (instead of the
     * combination of given IP and port).
     * 
     * @param id
     */
    public void establishConnection(InetAddress remote_ip, int remote_port, String assigned_id) {
        Predicate<Connection> pred = c -> c.getInetAddress().equals(remote_ip) && c.getPort() == remote_port;
        Connection conn = booting.stream().filter(pred).findFirst().get();
        booting.remove(conn);
        established.put(assigned_id, conn);
        conn.setId(assigned_id);
    }

    public boolean isEstablishedConnection(String id) {
        return established.containsKey(id);
    }
}
