package gent.timdemey.cards.base.logic;

import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.PileDef;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public interface Rules {

    public int revalue(Card card);

    public boolean isAllowed(State state, PickUpDef def);

    public boolean isAllowed(State state, PutDownDef def);

    public boolean isAllowed(State state, TransferDef def);

    /**
     * Returns the default destination pile sort for transfering cards from a
     * given pile.
     * 
     * @param state
     * @param from
     * @return the destination to transfer cards to, or null if cards cannot be
     *         transfered
     */
    public String getDefaultTransferDestination(State state, String sort);

}
