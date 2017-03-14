package gent.timdemey.cards.base.state;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private final List<Player> players;
    private final String name;

    public Lobby(String name) {
        this.name = checkNotNull(name);
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }
    
    @Override
    public boolean equals(Object obj) {
         if (!(obj instanceof Lobby)){
             return false;
         }
         Lobby other = (Lobby) obj;
         return this.name.equals(other.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
