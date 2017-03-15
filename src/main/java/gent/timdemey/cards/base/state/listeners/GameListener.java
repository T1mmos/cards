package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.beans.B_GameState;
import gent.timdemey.cards.base.state.Player;

public interface GameListener {
    public void onPlayerAdded (Player p);
    public void onPlayerRemoved (Player p);
    public void onGameStateChanged (B_GameState s);
}
