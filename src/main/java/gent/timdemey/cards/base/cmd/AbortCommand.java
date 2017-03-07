package gent.timdemey.cards.base.cmd;

import java.util.Collections;
import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

public class AbortCommand implements Command {

    @Override
    public void execute(State state) {
        
    }

    @Override
    public void rollback(State state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAllowed(State state, Rules rules) {
        return true;
    }

    @Override
    public boolean isTemporary() {
        return false;
    }

    @Override
    public List<Command> cleanTemp(List<Command> temps) {
        
        
        return Collections.emptyList();
    }

}
