package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.state.Game;

public abstract class CLT_GameCommand extends Command {

    protected abstract boolean isAllowed(List<CLT_GameCommand> intermediates, Game state, Rules rules)
            throws ChainException;
    
    protected abstract void execute(List<CLT_GameCommand> prevs, Game state);

    protected abstract Command merge(List<CLT_GameCommand> prevs);

    protected abstract void rollback(Game state);

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
