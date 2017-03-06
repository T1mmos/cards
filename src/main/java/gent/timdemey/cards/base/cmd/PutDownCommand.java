package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;

public class PutDownCommand implements Command {

    private final PutDownDef def;

    public PutDownCommand(PutDownDef def) {
        this.def = def;
    }

    @Override
    public void execute(State state) {
        Pile frompile = state.getPlayer(def.to.playerId).getPileConfig().getPile(Sorts.TEMP, 0);
        Pile topile = state.getPile(def.to);

        topile.add(frompile.removeTop(def.howmany));
    }

    @Override
    public void rollback(State state) {
        Pile frompile = state.getPile(def.to);
        Pile topile = state.getPlayer(def.to.playerId).getPileConfig().getPile(Sorts.TEMP, 0);

        topile.add(frompile.removeTop(def.howmany));
    }

    @Override
    public boolean isAllowed(State state, Rules rules) {
        return rules.isAllowed(state, def);
    }

}
