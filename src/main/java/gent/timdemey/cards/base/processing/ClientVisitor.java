package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;

public class ClientVisitor implements Visitor {

    private final Messenger messenger;
    private final Game game;
    private final Rules rules;

    public ClientVisitor(Game game, Rules rules, Messenger messenger) {
        this.game = game;
        this.rules = rules;
        this.messenger = messenger;
    }

    @Override
    public void visit(ALL_TransferCommand cmd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ALL_GameCommand cmd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CLT_CreateLobby cmd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(CLT_Connect cmd) {
        messenger.write(new B_Message("unknown", cmd));
    }

    @Override
    public void visit(SRV_RejectConnect cmd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        Game.INSTANCE.setLocalId(cmd.assigned_id);
        messenger.write(new B_Message(Game.INSTANCE.getLocalId(), new CLT_RequestLobbyList()));
    }

    @Override
    public void visit(CLT_RequestLobbyList cmd) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(SRV_ReturnLobbyList cmd) {
        // TODO Auto-generated method stub

    }

}
