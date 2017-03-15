package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;

public class ServerVisitor implements Visitor {

    private final Rules rules;
    private final Messenger messenger;
    private final Processor processor;

    public ServerVisitor(Processor p, Messenger m, Rules r) {
        this.processor = p;
        this.messenger = m;
        this.rules = r;
    }

    @Override
    public void visit(CLT_GameCommand cmd) {

    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        String assigned_id = cmd.assigned_id;

        cmd.setDestination(assigned_id);
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(CLT_RequestGameList cmd) {
        
    }

    @Override
    public void visit(CLT_InitPlayer cmd) {
        Server srv = State.INSTANCE.getServer(cmd.getSource());
        srv.getPlayers().stream().forEach(p -> {
            Command ret = new SRV_InitPlayer(p.getId(), p.getName());
            ret.setSource("server");
            ret.setDestination(cmd.getSource());
            processor.process(ret);
        });

        srv.addPlayer(cmd.getSource(), cmd.name);
        Command ret = new SRV_InitPlayer(cmd.getSource(), cmd.name);
        ret.setSource("server");
        ret.setDestination("broadcast");
        processor.process(ret);
    }

    @Override
    public void visit(SRV_InitPlayer cmd) {
        messenger.write(new B_Message(cmd));
    }

    @Override
    public void visit(SRV_RemovePlayer cmd) {
        State.INSTANCE.getServer(cmd.getSource()).removePlayer(cmd.id);
        messenger.write(new B_Message(cmd));
    }
}
