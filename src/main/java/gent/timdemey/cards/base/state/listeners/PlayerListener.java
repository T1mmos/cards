package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.state.Player;

public interface PlayerListener {
    public void onNameChanged(Player p);
    public void onPileConfigChanged(Player p);
}
