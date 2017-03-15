package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import gent.timdemey.cards.base.net.Connection;
import gent.timdemey.cards.base.net.Messenger;
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
            Messenger m = new Messenger();
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
                    unique_id = StringUtils.getRandomString(8);
                } while (server.isPlayer(unique_id));

                // set up connection
                {
                    Connection conn = new Connection(csocket);
                    conn.setName(unique_id);

                    conn.addMessageListener(msg -> {
                        Processor.INSTANCE.process(msg.getCommand());
                    });
                    conn.addConnectionListener(c -> {
                        Command cmd = new SRV_RemovePlayer(c.getName());
                        cmd.setSource(server.getLocalId());
                        cmd.setDestination("broadcast");
                        Processor.INSTANCE.process(cmd);
                    });
                    m.addNewConnection(conn);
                    m.setNewConnectionId(unique_id);
                    conn.start();
                }
                
                // send acknowledgement
                {
                    Command c = new SRV_AcceptConnect(server.getLocalId(), unique_id);
                    c.setSource(server.getLocalId());
                    c.setDestination(unique_id);
                    Processor.INSTANCE.process(c);
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
