package gent.timdemey.cards.base.state;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private final List<Player> players;

    public Lobby() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);
    }
}
