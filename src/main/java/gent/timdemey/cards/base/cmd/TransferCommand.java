package gent.timdemey.cards.base.cmd;

import java.util.Arrays;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public class TransferCommand implements Command {

    private final ChainCommand pickAndPut;

    public TransferCommand(TransferDef def) {
        PickUpCommand pick = new PickUpCommand(new PickUpDef(def.from, def.howmany));
        PutDownCommand put = new PutDownCommand(new PutDownDef(def.to, def.howmany));
        this.pickAndPut = new ChainCommand(pick, put);
    }

    @Override
    public void execute(State state) {
        pickAndPut.execute(state);
    }

    @Override
    public void rollback(State state) {
        pickAndPut.rollback(state);
    }

    @Override
    public boolean isAllowed(State state, Rules rules) {
        return pickAndPut.isAllowed(state, rules);
    }

    @Override
    public String toString() {
        return "Transfer: " + Arrays.toString(pickAndPut.getCommands());
    }
}
