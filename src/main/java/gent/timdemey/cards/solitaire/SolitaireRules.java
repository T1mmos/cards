package gent.timdemey.cards.solitaire;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;

public class SolitaireRules implements Rules {

    public int revalue(Card card) {
        return card.getKind().getValue();
    }

    @Override
    public boolean isAllowed(State state, PickUpDef def) {
        Pile frompile = state.getPile(def.from);
        
        if (!frompile.isVisible(def.howmany)) {
            return false;
        }

        switch (def.from.sort) {
            case SolitaireSorts.STOCK:
                return false;
            case SolitaireSorts.TALON:
                return def.howmany == 1;
            case SolitaireSorts.TABLEAU:
                return true;
            case SolitaireSorts.FOUNDATION:
                return def.howmany == 1;
            default:
                throw new UnsupportedOperationException("Undefined rule: " + def);
        }
    }

    @Override
    public boolean isAllowed(State state, PutDownDef def) {
        Pile topile = state.getPile(def.to);
        Pile tmp = state.getPlayer(def.to.playerId).getPileConfig().getPile(Sorts.TEMP, 0);

        switch (def.to.sort) {
            case SolitaireSorts.STOCK:
            case SolitaireSorts.TALON:
                return false;
            case SolitaireSorts.TABLEAU:
                return tmp.peekCardAt(0).getSuit().getColor() != topile.peekCard().getSuit().getColor();
            case SolitaireSorts.FOUNDATION:
                Card tmpcard = tmp.peekCardAt(0);
                Card topcard = topile.peekCard();
                return def.howmany == 1 && tmpcard.getSuit() == topcard.getSuit()
                        && tmpcard.getKind().getValue() == topcard.getKind().getValue() - 1;
            default:
                throw new UnsupportedOperationException("Undefined rule: " + def);
        }
    }
}
