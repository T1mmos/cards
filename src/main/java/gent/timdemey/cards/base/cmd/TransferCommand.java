package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public class TransferCommand implements Command {

    private final TransferDef def;
    private final PickUpCommand pick;
    private final PutDownCommand put;

    public TransferCommand(TransferDef def) {
        this.def = def;
        this.pick = new PickUpCommand(new PickUpDef(def.from, def.howmany));
        this.put = new PutDownCommand(new PutDownDef(def.to, def.howmany));
    }

    @Override
    public void execute(State state) {
        pick.execute(state);
        put.execute(state);
    }

    @Override
    public void rollback(State state) {
        put.rollback(state);
        pick.rollback(state);
    }

    @Override
    public boolean isAllowed(State state, Rules rules) {
        return rules.isAllowed(state, def);
    }

    @Override
    public String toString() {
        return "TRANSFER: " + def.howmany + " cards from " + def.from + " to " + def.to;
    }
}
