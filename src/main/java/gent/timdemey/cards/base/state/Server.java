package gent.timdemey.cards.base.state;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gent.timdemey.cards.base.beans.BeanUtils;
import gent.timdemey.cards.base.state.listeners.ServerListener;

public class Server {

    private final String srvId;
    private final String localId;
    private final String name;
    private transient final List<Player> players;
    private transient final List<Game> games;

    private transient final List<ServerListener> listeners;
   

    public Server(String srvId, String localId, String name) {
        this.srvId = srvId;
        this.localId = localId;
        this.name = name;
        this.players = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public String getServerId (){
        return srvId;
    }
    
    public String getName (){
        return name;
    }
    
    public void addPlayer(String id, String name) {
        Player player = new Player(id, name);
        players.add(player);
        listeners.forEach(l -> l.onPlayerAdded(player));
    }

    public void removePlayer(String id) {
        Player player = players.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList()).get(0);
        players.remove(player);
        listeners.forEach(l -> l.onPlayerRemoved(player));
    }

    public Player getPlayer(String id) {
        return players.stream().filter(player -> player.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public boolean isLocalPlayer(String id) {
        return getLocalPlayer().getId().equals(id);
    }

    public boolean isPlayer(String id) {
        return players.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList()).size() != 0;
    }

    public String getLocalId() {
        return localId;
    }

 /*   public void setLocalId(String id) {
        this.localId = id;
        listeners.forEach(l -> l.onIdAssigned(localId));
    }*/

    public Player getLocalPlayer() {
        return getPlayer(localId);
    }

    public void addGame(Game game) {
        games.add(game);
        listeners.forEach(l -> l.onGameAdded(game));
    }

    public void removeGame(Game game) {
        games.remove(game);
        listeners.forEach(l -> l.onGameRemoved(game));
    }
    
    public void addServerListener(ServerListener listener){
        listeners.add(listener);
    }

    public List<Game> getGames() {
        return games;
    }

    public int getGameCount() {
        return games.size();
    }

    @Override
    public String toString() {
        return BeanUtils.small(this);
    }
}
