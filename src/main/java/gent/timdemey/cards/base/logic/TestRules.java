package gent.timdemey.cards.base.logic;

import gent.timdemey.cards.base.cmd.PileChangeCommand;
import gent.timdemey.cards.base.entities.State;

public class TestRules implements Rules {

    @Override
    public boolean isAllowed(State state, PileChangeCommand cmd) {
        return true; // go nuts!
    }
}
