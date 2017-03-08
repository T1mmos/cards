package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

/**
 * A command is an atomic piece that unconditionally executes a specific step,
 * and has the ability to undo that step as well.
 * 
 */
public abstract class Command {

    public abstract void execute(List<Command> prevs, State state);

    public void rollback(State state){
        if (getType() == CmdType.MERGER) {
            throw new UnsupportedOperationException("This is a merger command, this method shouldn't be called!");
        } else {
            throw new UnsupportedOperationException("This is not a merger command, please provide a rollback implementation!");
        }
    }

    public abstract CmdType getType();

    public Command merge(List<Command> prevs) {
        if (getType() == CmdType.MERGER) {
            throw new UnsupportedOperationException("This is a merger command, subclass should override this method!");
        } else {
            throw new UnsupportedOperationException("This is not a merger command, this method shouldn't be called!");
        }
    }

    public abstract boolean isAllowed(List<Command> prevs, State state, Rules rules) throws ChainException;
}
