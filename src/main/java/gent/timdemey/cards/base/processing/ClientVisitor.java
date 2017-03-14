package gent.timdemey.cards.base.processing;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;

public class ClientVisitor implements Visitor {

    private final Processor processor;
    private final Messenger messenger;
    private final Rules rules;
    private final List<CLT_GameCommand> intermediates;

    public ClientVisitor(Processor p, Messenger m, Rules rules) {
        this.processor = p;
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
        if (cmd.isAllowed(intermediates, Game.INSTANCE, rules)) {
            cmd.execute(intermediates, Game.INSTANCE);
        }

    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        Game.INSTANCE.setLocalId(cmd.assigned_id);
    }

    @Override
    public void visit(CLT_RequestLobbyList cmd) {
        cmd.setDestination("server");
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(CLT_InitPlayer cmd) {
        cmd.setDestination("server");
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(SRV_InitPlayer cmd) {
        Game.INSTANCE.addPlayer(cmd.id, cmd.name);
    }
}
