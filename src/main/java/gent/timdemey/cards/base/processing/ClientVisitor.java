package gent.timdemey.cards.base.processing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Connection;
import gent.timdemey.cards.base.net.ConnectionManager;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;

public class ClientVisitor implements Visitor {

    private static final Logger logger = LogManager.getLogger("ClientVisitor");
    private final Rules rules;
    private final List<CLT_GameCommand> intermediates;

    public ClientVisitor(Rules rules) {
        this.rules = rules;
        this.intermediates = new ArrayList<>();
    }

    @Override
    public void visit(CLT_GameCommand cmd) {
        // this is gonna be a complex method. A game command
        // may not be allowed in the current state, even when
        // coming from the server. If this is the case, we are
        // 100% sure that a rollback is needed because it
        // indicates that the local state is not synced with the
        // correct state at server side.
        // Basically, a game command must always be checked
        // against the current state, independent of source.

        // TODO: implement the entire rollback mechanism.
        // Use the specific methods for GameCommand.
        // if (cmd.isAllowed(intermediates, Game.INSTANCE, rules)) {
        // cmd.execute(intermediates, Game.INSTANCE);
        // }

    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        System.out.println("ACCEPTED!!" + cmd);
        State.INSTANCE.addServer(new Server(cmd.server_id, cmd.assigned_id, cmd.server_name));
        
        //conn.addMessageListener(m -> Processor.INSTANCE.process(m.getCommand()));
       
    }

    @Override
    public void visit(CLT_RequestGameList cmd) {

    }

    @Override
    public void visit(SRV_AddPlayer cmd) {
        State.INSTANCE.getServer(cmd.getSourceID()).addPlayer(cmd.id, cmd.name);
    }

    @Override
    public void visit(SRV_RemovePlayer cmd) {
        State.INSTANCE.getServer(cmd.getSourceID()).removePlayer(cmd.id);
    }

    @Override
    public void visit(CLT_JoinServer cmd) {
        try {
            Socket socket = new Socket(cmd.ip, cmd.port);
            Connection conn = ConnectionManager.newConnection(socket);
            conn.addMessageListener(msg -> Processor.INSTANCE.process(msg.getCommand()));
            ConnectionManager.startConnection(conn, cmd.server_id);
            cmd.unicast(cmd.server_id);
        } catch (IOException e) {
            handle(e);
        }
    }

    private void handle(Exception e) {
        logger.error("LOGGING ERROR: (please handle better, e.g. with GUI dialog)");
        logger.catching(e);
    }
}
