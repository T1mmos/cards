package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PojoUtils;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;

/**
 * Pick up cards from a real pile and put them in the temporary pile.
 * 
 *
 */
public class PickUpCommand extends Command {

    private final PickUpDef def;

    public PickUpCommand(PickUpDef def) {
        this.def = def;
    }

    public PickUpDef getDef() {
        return def;
    }

    @Override
    public void execute(List<Command> prevs, State state) {
        Pile frompile = state.getPile(def.from);
        Pile topile = state.getPlayer(def.from.playerId).getPileConfig().getPile(Sorts.TEMP, 0);
        topile.add(frompile.removeTop(def.howmany));
    }

    @Override
    public void rollback(State state) {
        Pile frompile = state.getPlayer(def.from.playerId).getPileConfig().getPile(Sorts.TEMP, 0);
        Pile topile = state.getPile(def.from);
        topile.add(frompile.removeTop(def.howmany));
    }

    @Override
    public boolean isAllowed(List<Command> prevs, State state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 0);

        Pile frompile = state.getPile(def.from);
        if (def.howmany > frompile.size()) {
            return false;
        }

        return rules.canPickUp(state, def);
    }
    
    @Override
    public String toString() {
        return PojoUtils.pretty(def);
    }

    @Override
    public CmdType getType() {
        return CmdType.INTERMEDIATE;
    }
    
    
}
