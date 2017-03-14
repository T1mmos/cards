package gent.timdemey.cards.base.logic;

import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.beans.B_Card;
import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.processing.CLT_TransferCommand;

public interface Rules {

    public int getMaxPlayers();
    
    public int revalue(B_Card card);

    public boolean canPickUp(Game state, CLT_PickUp cmd);

    public boolean canPutDown(Game state, CLT_PutDown cmd, B_Pile pile);
    
    public boolean canAutoTransfer(Game state, CLT_TransferCommand cmd);
    
    

    /**
     * Returns the default destination pile sort for transfering cards from a
     * given pile.
     * 
     * @param state
     * @param from
     * @return the destination to transfer cards to, or null if cards cannot be
     *         transfered
     */
    public String getAutoTransferDestination(Game state, String sort);

}
