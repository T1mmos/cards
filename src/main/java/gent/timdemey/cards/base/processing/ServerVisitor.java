package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.B_GameState;
import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.utils.StringUtils;

public class ServerVisitor implements Visitor {

    private final Rules rules;
    private final Messenger messenger;

    public ServerVisitor(Rules rules, Messenger messenger) {
        this.rules = rules;
        this.messenger = messenger;
    }

    @Override
    public void visit(CLT_Connect cmd) {
        if (Game.INSTANCE.getGameState() != B_GameState.Lobby) {
            messenger.send(new B_Message(Game.INSTANCE.getLocalId(), new SRV_RejectConnect("Game already started.")));
            return;
        }
        if (Game.INSTANCE.getPlayerCount() == rules.getMaxPlayers()) {
            messenger.send(new B_Message(Game.INSTANCE.getLocalId(), new SRV_RejectConnect("Game full.")));
            return;
        }

        String unique_id;
        do {
            unique_id = StringUtils.getRandomString(16);
        } while (Game.INSTANCE.isPlayer(unique_id));
        Game.INSTANCE.addPlayer(unique_id, cmd.name);
        messenger.send(new B_Message(Game.INSTANCE.getLocalId(), new SRV_AcceptConnect(unique_id)));

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
    public void visit(SRV_RejectConnect cmd) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(SRV_AcceptConnect cmd) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void visit(CLT_RequestLobbyList cmd) {
        
    }

    @Override
    public void visit(SRV_ReturnLobbyList cmd) {
        // TODO Auto-generated method stub
        
    }
}
