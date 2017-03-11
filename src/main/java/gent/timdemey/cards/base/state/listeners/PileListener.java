package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.beans.B_Card;

public interface PileListener {
    public void cardAdded (B_Card c);
    public void cardRemoved (B_Card c);
}
