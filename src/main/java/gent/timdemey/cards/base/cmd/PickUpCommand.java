package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;

/**
 * Pick up cards from a real pile and put them in the temporary pile.
 * 
 *
 */
public class PickUpCommand implements Command {

    private final PickUpDef def;

    public PickUpCommand(PickUpDef def) {
        this.def = def;
    }

    @Override
    public void execute(State state) {
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
    public boolean isAllowed(State state, Rules rules) {
        Pile frompile = state.getPile(def.from);
        if (def.howmany > frompile.size()) {
            return false;
        }

        return rules.isAllowed(state, def);
    }

    @Override
    public String toString() {
        return "PICK UP " + def.howmany + " from (" + def.from.playerId + ";" + def.from.sort + ";"
                + def.from.pileIdx + ") to TEMP";
    }
}
