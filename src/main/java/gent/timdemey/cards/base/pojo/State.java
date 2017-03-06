package gent.timdemey.cards.base.pojo;

import java.util.List;
import java.util.stream.Collectors;

public class State {

    private final List<Player> players;
    private final Player ghost;
    private final int localIDX;

    public State(List<Player> players, Player ghost, int localIDX) {
        this.players = players;
        this.ghost = ghost;
        this.localIDX = localIDX;
    }

    public Player getPlayer(String id) {
        return players.stream().filter(player -> player.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    public Player getLocalPlayer() {
        return players.get(localIDX);
    }
    
    public Pile getPile(PileDef def){
        return getPlayer(def.playerId).getPileConfig().getPile(def.sort, def.pileIdx);
    }

    /**
     * The ghost player is the virtual player to which all cards and piles are
     * assigned that are not assigned to a real player. For example, all cards
     * that have been played do not belong to a real player anymore, and can be
     * removed from the game or assigned to common piles. Those piles belong to
     * the ghost player.
     * 
     * @return
     */
    public Player getGhostPlayer() {
        return ghost;
    }
}
