package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.state.Player;

public interface PlayerListener {
    public void nameChanged(Player p);
    public void pileConfigChanged(Player p);
}
