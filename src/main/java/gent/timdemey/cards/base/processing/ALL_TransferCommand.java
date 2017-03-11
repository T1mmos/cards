package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileDef;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.state.Game;

public class ALL_TransferCommand extends ALL_GameCommand {

    /**
     * The pile where the cards are currently held in.
     */
    public final B_PileDef from;
    /**
     * The pile where the cards are being transfered to.
     */
    public final B_PileDef to;
    /**
     * How many cards are being transfered.
     */
    public final int howmany;

    public ALL_TransferCommand(B_PileDef from, B_PileDef to, int howmany) {
        this.from = from;
        this.to = to;
        this.howmany = howmany;
    }

    @Override
    public String toString() {
        return "TRANSFER: " + howmany + " cards from " + from + " to " + to;
    }

    @Override
    protected void execute(List<ALL_GameCommand> prevs, Game state) {
        B_Pile frompile = state.getPile(from);
        B_Pile topile = state.getPile(to);

        topile.add(frompile.removeTop(howmany));
    }

    @Override
    protected void rollback(Game state) {
        B_Pile frompile = state.getPile(to);
        B_Pile topile = state.getPile(from);

        topile.add(frompile.removeTop(howmany));
    }

    @Override
    protected boolean isAllowed(List<ALL_GameCommand> prevs, Game state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 0);

        CLT_PickUp pickup = new CLT_PickUp(from, howmany);
        CLT_PutDown putdown = new CLT_PutDown(to);
        B_Pile tmpPile = state.getPile(from).peekPile(howmany);
        return rules.canPickUp(state, pickup) && rules.canPutDown(state, putdown, tmpPile);
    }

    @Override
    protected Command merge(List<ALL_GameCommand> prevs) {
        throw new UnsupportedOperationException();
    }
}
