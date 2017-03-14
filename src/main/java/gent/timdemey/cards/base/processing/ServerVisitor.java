package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;

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
        Game.INSTANCE.addPlayer(cmd.getSource(), cmd.name);
        System.out.println(Game.INSTANCE.getPlayerCount());
        System.out.println(Game.INSTANCE.getPlayer(cmd.getSource()));
    }
}
