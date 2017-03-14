package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;

public interface GameListener {
    public void stateChanged(Game state);

    public void playerAdded(Player p);

    public void idAssigned(String id);
}
