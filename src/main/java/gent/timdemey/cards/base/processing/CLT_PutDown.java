package gent.timdemey.cards.base.processing;

import java.util.List;

import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileDef;
import gent.timdemey.cards.base.beans.BeanUtils;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Sorts;

public class CLT_PutDown extends CLT_GameCommand {

    public final B_PileDef to;

    public CLT_PutDown(B_PileDef to) {
        this.to = to;
    }

    @Override
    protected void execute(List<CLT_GameCommand> prevs, Game state) {
        B_Pile frompile = state.getPlayer(to.playerId).getPileConfig().getPile(Sorts.DRAG, 0);
        B_Pile topile = state.getPile(to);
        topile.add(frompile.remove(0));
    }

    @Override
    protected boolean isAllowed(List<CLT_GameCommand> prevs, Game state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 1);
        ChainException.checkType(prevs, this, 0, CLT_PickUp.class);

        B_Pile tmp = state.getPlayer(to.playerId).getPileConfig().getPile(Sorts.DRAG, 0);
        return rules.canPutDown(state, this, tmp);
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }

    @Override
    protected Command merge(List<CLT_GameCommand> prevs) {
        CLT_PickUp pickup = (CLT_PickUp) prevs.get(0);
        return new CLT_TransferCommand(pickup.from, to, pickup.howmany);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected void rollback(Game state) {
        throw new UnsupportedOperationException();
    }
}
