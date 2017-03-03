package gent.timdemey.cards.base.logic;

import gent.timdemey.cards.base.cmd.PileChangeCommand;
import gent.timdemey.cards.base.pojo.State;

public interface Rules {
    public boolean isAllowed (State state, PileChangeCommand cmd);
}
