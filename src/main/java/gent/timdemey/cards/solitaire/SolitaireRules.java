package gent.timdemey.cards.solitaire;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.Kind;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PutDownDef;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public class SolitaireRules implements Rules {

    public int revalue(Card card) {
        return card.getKind().getValue();
    }

    @Override
    public boolean canPickUp(State state, PickUpDef def) {
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
    public boolean canPutDown(State state, PutDownDef def, Pile pile) {
        Pile topile = state.getPile(def.to);

        switch (def.to.sort) {
            case SolitaireSorts.STOCK:
            case SolitaireSorts.TALON:
                return false;
            case SolitaireSorts.TABLEAU:
                return pile.peekCardAt(0).getSuit().getColor() != topile.peekCard().getSuit().getColor();
            case SolitaireSorts.FOUNDATION:
                Card tmpcard = pile.peekCardAt(0);
                Card topcard = topile.peekCard();
                return pile.size() == 1 && tmpcard.getSuit() == topcard.getSuit()
                        && tmpcard.getKind().getValue() == topcard.getKind().getValue() - 1;
            default:
                throw new UnsupportedOperationException("Undefined rule: " + def);
        }
    }
    
    @Override
    public String getAutoTransferDestination(State state, String sort) {
        switch (sort) {
            case SolitaireSorts.STOCK:
                return SolitaireSorts.TALON;
            case SolitaireSorts.TALON:                
            case SolitaireSorts.TABLEAU:
                return SolitaireSorts.FOUNDATION;
            case SolitaireSorts.FOUNDATION:
                return null;
            default:
                throw new UnsupportedOperationException("Undefined sort: " + sort);
        }
    }

    @Override
    public boolean canAutoTransfer(State state, TransferDef def) {
        switch (def.from.sort) {
            case SolitaireSorts.STOCK:
                return def.howmany == 3 && SolitaireSorts.TALON.equals(def.to.sort);
            case SolitaireSorts.TALON:
            case SolitaireSorts.TABLEAU:
                if (def.howmany != 1 || !SolitaireSorts.FOUNDATION.equals(def.to.sort)) {
                    return false;
                }
                Pile to = state.getPile(def.to);
                Pile from = state.getPile(def.from);
                if (from.size() == 0) {
                    return false;
                }
                Card card = from.peekCard();
                if (to.size() == 0 && card.getKind() != Kind.ACE) {
                    return false;
                }
                if (to.size() != 0 && card.getKind().getValue() != to.peekCard().getKind().getValue() + 1) {
                    return false;
                }
                return true;
            case SolitaireSorts.FOUNDATION:
                return false;
            default:
                throw new UnsupportedOperationException("Undefined rule: " + def);
        }
    }
}
