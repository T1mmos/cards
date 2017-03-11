package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.state.Game;

public class CLT_AbortPickUp extends ALL_GameCommand {

    @Override
    protected void execute(List<ALL_GameCommand> prevs, Game state) {
        CLT_PickUp pickup = (CLT_PickUp) prevs.get(0);
        pickup.rollback(state);
    }

    @Override
    protected boolean isAllowed(List<ALL_GameCommand> prevs, Game state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 1);
        ChainException.checkType(prevs, this, 0, CLT_PickUp.class);
        return true;
    }

    @Override
    protected Command merge(List<ALL_GameCommand> prevs) {
        return null; // clear temp commands
    }

    @Override
    protected void rollback(Game state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
