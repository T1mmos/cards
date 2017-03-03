package gent.timdemey.cards.solitaire;

import gent.timdemey.cards.base.cmd.PileChangeCommand;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileChange;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;

public class SolitaireRules implements Rules {

    @Override
    public boolean isAllowed(State state, PileChangeCommand cmd) {
        PileChange c = cmd.getChange();
        if (Sorts.HAND.equals(c.from_sort)) {
            switch (cmd.getChange().to_sort) {
            case Sorts.HAND:
            case SolitaireSorts.STOCK:
            case SolitaireSorts.TALON:
                return false;
            case SolitaireSorts.FOUNDATION:
                // TODO
                Pile pile = state.getLocalPlayer().getPileConfig().getPile(SolitaireSorts.FOUNDATION, c.to_pileIdx);
            case SolitaireSorts.TABLEAU:
                // TODO
            }
            
        }
        throw new UnsupportedOperationException();
    }

}
