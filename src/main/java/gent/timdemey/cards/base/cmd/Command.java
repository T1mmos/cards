package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.entities.State;
import gent.timdemey.cards.base.logic.Rules;

/**
 * A command is a thing that unconditionally executes a specific step, and
 * has the ability to undo that step as well. 
 * 
 */
public interface Command {
    public void execute (State state);
    public void rollback (State state);
    public boolean isAllowed(State state, Rules rules);
}
