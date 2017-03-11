package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.state.Game;

public abstract class ALL_GameCommand implements Command {

    protected abstract boolean isAllowed(List<ALL_GameCommand> intermediates, Game state, Rules rules)
            throws ChainException;

    protected abstract void execute(List<ALL_GameCommand> prevs, Game state);

    protected abstract Command merge(List<ALL_GameCommand> prevs);

    protected abstract void rollback(Game state);

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
