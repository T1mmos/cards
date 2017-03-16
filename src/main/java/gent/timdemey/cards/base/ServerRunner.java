package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;

import gent.timdemey.cards.base.net.Connection;
import gent.timdemey.cards.base.net.ConnectionManager;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.SRV_RemovePlayer;
import gent.timdemey.cards.base.processing.ServerVisitor;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.utils.StringUtils;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ServerRunner {

    private static final Logger logger = LogManager.getLogger("ServerRunner", new StringFormatterMessageFactory());
    
    private Thread thread;
    private volatile ServerSocket ssocket;

    public ServerRunner() {
        this.thread = new Thread(() -> listen(), "Server Socket");
    }

    public void start(int port) throws IOException {
        if (thread == null) {
            throw new IllegalStateException("Has already run, create new instance instead");
        }
        this.ssocket = new ServerSocket(port);
        thread.start();
    }

    public void stop() {
        try {
            ssocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            thread = null;
            ssocket = null;
        }
    }

    private void listen() {
        try {
            Visitor v = new ServerVisitor(new SolitaireRules());
            Processor.INSTANCE.addVisitor(v);

            String id = "SRV-" + StringUtils.getRandomString(4);
            final Server server = new Server(id, id, "My Awesome Card Server!");
            State.INSTANCE.addServer(server);

            while (true) {
                Socket csocket = ssocket.accept();

                // unique id
                String unique_id = null;
                do {
                    unique_id = "CLT-" + StringUtils.getRandomString(4);
                } while (server.isPlayer(unique_id));

                // set up connection
                Connection conn = ConnectionManager.newEstablishedConnection(csocket, unique_id);

                conn.addMessageListener(msg -> {
                    Processor.INSTANCE.process(msg.getCommand());
                });
                conn.addConnectionListener(c -> {
                    new SRV_RemovePlayer(c.getId()).setSourceID(server.getLocalId()).broadcast();
                });

                // send acknowledgement which sets up the local ID at client
                // side
                new SRV_AcceptConnect(server.getLocalId(), unique_id, server.getName()).setSourceID(id)
                        .unicast(unique_id);
            }
        } catch (SocketException e) {
            logger.info("Server stopped listening for connections");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {            
            ConnectionManager.dropAllConnections();
        }
    }
}
