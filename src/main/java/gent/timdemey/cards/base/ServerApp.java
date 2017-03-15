package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import gent.timdemey.cards.base.net.Connection;
import gent.timdemey.cards.base.net.ConnectionManager;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.SRV_RemovePlayer;
import gent.timdemey.cards.base.processing.ServerVisitor;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.utils.StringUtils;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ServerApp {
    public static void main(String[] args) {
        try (ServerSocket ssocket = new ServerSocket(666)) {
            ConnectionManager m = new ConnectionManager();
            Visitor v = new ServerVisitor(m, new SolitaireRules());
            Processor.INSTANCE.addVisitor(v);

            String id = "SRV-" + StringUtils.getRandomString(4);
            final Server server = new Server(id, id);
            State.INSTANCE.addServer(server);

            while (true) {
                Socket csocket = ssocket.accept();

                // unique id
                String unique_id = null;
                do {
                    unique_id = "CLT-" + StringUtils.getRandomString(4);
                } while (server.isPlayer(unique_id));

                // set up connection
                {
                    Connection conn = new Connection(csocket);
                    conn.setId(unique_id);

                    conn.addMessageListener(msg -> {
                        Processor.INSTANCE.process(msg.getCommand());
                    });
                    conn.addConnectionListener(c -> {
                        Command cmd = new SRV_RemovePlayer(c.getName());
                        cmd.setSourceID(server.getLocalId());
                        cmd.setDestinationID("broadcast");
                        Processor.INSTANCE.process(cmd);
                    });
                    m.addBootingConnection(conn);
                    m.establishConnection(conn.getInetAddress(), conn.getPort(), unique_id);
                    conn.start();
                }
                
                // send acknowledgement
                {
                    Command c = new SRV_AcceptConnect(server.getLocalId(), unique_id);
                    c.setSourceID(id);
                    c.setDestinationID(unique_id);
                    Processor.INSTANCE.process(c);
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
