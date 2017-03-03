package gent.timdemey.cards.base.logic;

import gent.timdemey.cards.base.cmd.PileChangeCommand;
import gent.timdemey.cards.base.entities.State;

public interface Rules {
    public boolean isAllowed (State state, PileChangeCommand cmd);
}
