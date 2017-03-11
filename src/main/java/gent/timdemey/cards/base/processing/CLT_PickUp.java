package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileDef;
import gent.timdemey.cards.base.beans.PojoUtils;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Sorts;

/**
 * Pick up cards from a real pile and put them in the temporary pile.
 * 
 *
 */
public class CLT_PickUp extends ALL_GameCommand {

    public final B_PileDef from;
    public final int howmany;

    public CLT_PickUp(B_PileDef from, int howmany) {
        this.from = from;
        this.howmany = howmany;
    }

    @Override
    protected void execute(List<ALL_GameCommand> prevs, Game state) {
        B_Pile frompile = state.getPile(from);
        B_Pile topile = state.getPlayer(from.playerId).getPileConfig().getPile(Sorts.DRAG, 0);
        topile.add(frompile.removeTop(howmany));
    }

    @Override
    protected void rollback(Game state) {
        B_Pile frompile = state.getPlayer(from.playerId).getPileConfig().getPile(Sorts.DRAG, 0);
        B_Pile topile = state.getPile(from);
        topile.add(frompile.removeTop(howmany));
    }

    @Override
    protected boolean isAllowed(List<ALL_GameCommand> prevs, Game state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 0);

        B_Pile frompile = state.getPile(from);
        if (howmany > frompile.size()) {
            return false;
        }

        return rules.canPickUp(state, this);
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected Command merge(List<ALL_GameCommand> prevs) {
        return null;
    }
}
