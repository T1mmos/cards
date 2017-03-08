package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public class TransferCommand extends Command {

    protected final TransferDef def;

    public TransferCommand(TransferDef def) {
        this.def = def;
    }

    @Override
    public String toString() {
        return "TRANSFER: " + def.howmany + " cards from " + def.from + " to " + def.to;
    }

    @Override
    public void execute(List<Command> prevs, State state) {
        Pile from = state.getPile(def.from);
        Pile to = state.getPile(def.to);

        to.add(from.removeTop(def.howmany));
    }

    @Override
    public void rollback(State state) {
        Pile from = state.getPile(def.to);
        Pile to = state.getPile(def.from);

        to.add(from.removeTop(def.howmany));
    }

    @Override
    public boolean isAllowed(List<Command> prevs, State state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 0);

        PickUpDef pickup = new PickUpDef(def.from, def.howmany);
        PutDownDef putdown = new PutDownDef(def.to, def.howmany);
        Pile tmpPile = state.getPile(def.from).peekPile(def.howmany);
        return rules.canPickUp(state, pickup) && rules.canPutDown(state, putdown, tmpPile);
    }

    @Override
    public CmdType getType() {
        return CmdType.STANDALONE;
    }
}
