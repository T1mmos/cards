package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;

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
    public void visit(CLT_RequestLobbyList cmd) {
        System.out.println("Lobby list requested by: " + cmd.getSource() + " ("
                + Game.INSTANCE.getPlayer(cmd.getSource()).getName() + ")");
    }

    @Override
    public void visit(CLT_InitPlayer cmd) {
        Game.INSTANCE.getPlayers().stream().forEach(p -> {
            Command ret = new SRV_InitPlayer(p.getId(), p.getName());
            ret.setSource("server");
            ret.setDestination(cmd.getSource());
            processor.process(ret);
        });

        Game.INSTANCE.addPlayer(cmd.getSource(), cmd.name);
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
        Game.INSTANCE.removePlayer(cmd.id);
        messenger.write(new B_Message(cmd));
    }
}
