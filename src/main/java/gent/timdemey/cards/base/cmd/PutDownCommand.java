package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileDef;
import gent.timdemey.cards.base.pojo.PojoUtils;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public class PutDownCommand extends Command {

    private final PutDownDef def;

    public PutDownCommand(PutDownDef def) {
        this.def = def;
    }

    public PutDownDef getDef() {
        return def;
    }

    @Override
    public void execute(List<Command> prevs, State state) {
        Pile frompile = state.getPlayer(def.to.playerId).getPileConfig().getPile(Sorts.TEMP, 0);
        Pile topile = state.getPile(def.to);
        topile.add(frompile.remove(0));
    }

    @Override
    public boolean isAllowed(List<Command> prevs, State state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 1);
        ChainException.checkType(prevs, this, 0, PickUpCommand.class);

        Pile tmp = state.getPlayer(def.to.playerId).getPileConfig().getPile(Sorts.TEMP, 0);
        return rules.canPutDown(state, def, tmp);
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(def);
    }

    @Override
    public Command merge(List<Command> prevs) {
        PickUpCommand pickup = (PickUpCommand) prevs.get(0);
        TransferDef tdef = new TransferDef(pickup.getDef().from, this.getDef().to, pickup.getDef().howmany);
        return new TransferCommand(tdef);
    }

    @Override
    public CmdType getType() {
        return CmdType.MERGER;
    }
}
