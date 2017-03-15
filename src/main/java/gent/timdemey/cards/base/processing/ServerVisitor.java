package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.ConnectionManager;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;

public class ServerVisitor implements Visitor {

    private final Rules rules;
    private final ConnectionManager messenger;

    public ServerVisitor(ConnectionManager m, Rules r) {
        this.messenger = m;
        this.rules = r;
    }

    @Override
    public void visit(CLT_GameCommand cmd) {

    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        String assigned_id = cmd.assigned_id;

        cmd.setDestinationID(assigned_id);
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(CLT_RequestGameList cmd) {

    }

    @Override
    public void visit(CLT_InitPlayer cmd) {
        Server srv = State.INSTANCE.getServer(cmd.getDestinationID());
        srv.getPlayers().stream().forEach(p -> {
            Command ret = new SRV_AddPlayer(p.getId(), p.getName());
            ret.setSourceID(srv.getLocalId());
            ret.setDestinationID(cmd.getSourceID());
            Processor.INSTANCE.process(ret);
        });

        srv.addPlayer(cmd.getSourceID(), cmd.name);
        Command ret = new SRV_AddPlayer(cmd.getSourceID(), cmd.name);
        ret.setSourceID(srv.getLocalId());
        ret.setDestinationID("broadcast");
        Processor.INSTANCE.process(ret);
    }

    @Override
    public void visit(SRV_AddPlayer cmd) {
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(SRV_RemovePlayer cmd) {
        State.INSTANCE.getServer(cmd.getSourceID()).removePlayer(cmd.id);
        messenger.write(new B_Message(cmd));
    }
}
