package gent.timdemey.cards.base.processing;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;

public class ClientVisitor implements Visitor {

    private final Messenger messenger;
    private final Rules rules;
    private final List<CLT_GameCommand> intermediates;

    public ClientVisitor(Messenger m, Rules rules) {
        this.messenger = m;
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
        messenger.setNewConnectionId(cmd.server_id);
        State.INSTANCE.addServer(new Server(cmd.getSource(), cmd.assigned_id));
    }

    @Override
    public void visit(CLT_RequestGameList cmd) {
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(CLT_InitPlayer cmd) {
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(SRV_AddPlayer cmd) {
        State.INSTANCE.getServer(cmd.getSource()).addPlayer(cmd.id, cmd.name);
    }

    @Override
    public void visit(SRV_RemovePlayer cmd) {
        State.INSTANCE.getServer(cmd.getSource()).removePlayer(cmd.id);
    }
}
