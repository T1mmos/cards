package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;

public interface ServerListener {

    public void onPlayerAdded(Player p);

    public void onPlayerRemoved(Player p);
    
    public void onGameAdded (Game g);
    
    public void onGameRemoved (Game g);
}
