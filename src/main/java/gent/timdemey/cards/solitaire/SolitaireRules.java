package gent.timdemey.cards.solitaire;

import gent.timdemey.cards.base.beans.B_Card;
import gent.timdemey.cards.base.beans.B_Kind;
import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.processing.CLT_PickUp;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.processing.CLT_TransferCommand;
import gent.timdemey.cards.base.state.Game;

public class SolitaireRules implements Rules {

    public int revalue(B_Card card) {
        return card.getKind().getValue();
    }

    @Override
    public boolean canPickUp(Game state, PickUpDef def) {
        B_Pile frompile = state.getPile(def.from);

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
    public boolean canPutDown(Game state, PutDownDef def, B_Pile pile) {
        B_Pile topile = state.getPile(def.to);

        switch (def.to.sort) {
            case SolitaireSorts.STOCK:
            case SolitaireSorts.TALON:
                return false;
            case SolitaireSorts.TABLEAU:
                return pile.peekCardAt(0).getSuit().getColor() != topile.peekCard().getSuit().getColor();
            case SolitaireSorts.FOUNDATION:
                B_Card tmpcard = pile.peekCardAt(0);
                B_Card topcard = topile.peekCard();
                return pile.size() == 1 && tmpcard.getSuit() == topcard.getSuit()
                        && tmpcard.getKind().getValue() == topcard.getKind().getValue() - 1;
            default:
                throw new UnsupportedOperationException("Undefined rule: " + def);
        }
    }
    
    @Override
    public String getAutoTransferDestination(Game state, String sort) {
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
    public boolean canAutoTransfer(Game state, TransferDef def) {
        switch (def.from.sort) {
            case SolitaireSorts.STOCK:
                return def.howmany == 3 && SolitaireSorts.TALON.equals(def.to.sort);
            case SolitaireSorts.TALON:
            case SolitaireSorts.TABLEAU:
                if (def.howmany != 1 || !SolitaireSorts.FOUNDATION.equals(def.to.sort)) {
                    return false;
                }
                B_Pile to = state.getPile(def.to);
                B_Pile from = state.getPile(def.from);
                if (from.size() == 0) {
                    return false;
                }
                B_Card card = from.peekCard();
                if (to.size() == 0 && card.getKind() != B_Kind.ACE) {
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

    @Override
    public int getMaxPlayers() {
        return 2;
    }

    @Override
    public boolean canPickUp(Game state, CLT_PickUp cmd) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canPutDown(Game state, CLT_PutDown cmd, B_Pile pile) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canAutoTransfer(Game state, CLT_TransferCommand cmd) {
        // TODO Auto-generated method stub
        return false;
    }
}
