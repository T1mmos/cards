package gent.timdemey.cards.base.state;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.beans.B_GameState;
import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileDef;
import gent.timdemey.cards.base.beans.BeanUtils;
import gent.timdemey.cards.base.state.listeners.GameListener;

public final class Game {

    private final String name;
    private final List<Player> players;

    private transient final List<GameListener> listeners;

    private B_GameState state;

    public Game(String name, String password) {
        this.name = checkNotNull(name);
        this.state = B_GameState.Lobby;
        this.players = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public Player getPlayer (String id){
        return players.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void addPlayer(Player p) {
        players.add(p);
        listeners.forEach(l -> l.onPlayerAdded(p));
    }

    public void removePlayer(Player p) {
        players.remove(p);
        listeners.forEach(l -> l.onPlayerRemoved(p));
    }

    public B_GameState getGameState() {
        return state;
    }

    public void setGameState(B_GameState state) {
        this.state = state;
        listeners.forEach(l -> l.onGameStateChanged(state));
    }

    public B_Pile getPile (B_PileDef def){
        return getPlayer(def.playerId).getPileConfig().getPile(def.sort, def.pileIdx);
    }
    
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        Game other = (Game) obj;
        return this.name.equals(other.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }
}
