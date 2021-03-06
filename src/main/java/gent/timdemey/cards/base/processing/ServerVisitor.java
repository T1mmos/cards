package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.ConnectionManager;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.utils.StringUtils;

public class ServerVisitor implements Visitor {

    private final Rules rules;

    public ServerVisitor(Rules r) {
        this.rules = r;
    }

    @Override
    public void visit(CLT_GameCommand cmd) {

    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        ConnectionManager.write(new B_Message(cmd));
    }

    @Override
    public void visit(CLT_RequestGameList cmd) {

    }

    @Override
    public void visit(SRV_AddPlayer cmd) {
        //
    }

    @Override
    public void visit(SRV_RemovePlayer cmd) {
        State.INSTANCE.getServer(cmd.getSourceID()).removePlayer(cmd.id);
        ConnectionManager.write(new B_Message(cmd));
    }

    @Override
    public void visit(CLT_JoinServer cmd) {
        // TODO Auto-generated method stub
        
    }
}
