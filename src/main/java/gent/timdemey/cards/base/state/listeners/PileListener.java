package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.beans.B_Card;

public interface PileListener {
    public void onCardAdded (B_Card c);
    public void onCardRemoved (B_Card c);
}
