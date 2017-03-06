package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

/**
 * A command is an atomic piece that unconditionally executes a specific step,
 * and has the ability to undo that step as well.
 * 
 */
public interface Command {
    
    public void execute(State state);

    public void rollback(State state);

    public boolean isAllowed(State state, Rules rules);
}
