package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

public class PickUpAbortCommand extends Command {

    @Override
    public void execute(List<Command> prevs, State state) {
        PickUpCommand pickup = (PickUpCommand) prevs.get(0);
        pickup.rollback(state);
    }

    @Override
    public boolean isAllowed(List<Command> prevs, State state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 1);
        ChainException.checkType(prevs, this, 0, PickUpCommand.class);
        return true;
    }

    @Override
    public CmdType getType() {
        return CmdType.MERGER;
    }
    
    @Override
    public Command merge(List<Command> prevs) {
        return null; // clear temp commands
    }
}
