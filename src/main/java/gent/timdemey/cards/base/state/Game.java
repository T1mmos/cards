package gent.timdemey.cards.base.state;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gent.timdemey.cards.base.beans.B_GameState;
import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileDef;
import gent.timdemey.cards.base.beans.BeanUtils;

public enum Game {

    INSTANCE;
    
    private B_GameState state;
    private final List<Lobby> lobbies;
    private final List<Player> players;
    private String localId;

    private Game() {
        this.state = B_GameState.Lobby;
        this.players = new ArrayList<>();
        this.lobbies = new ArrayList<>();
    }
    
    public String getLocalId (){
        return localId;
    }
    
    public void setLocalId (String id){
        this.localId = id;
    }
    
    public B_GameState getGameState (){
        return state;
    }
    
    public void setGameState (B_GameState state){
        this.state = state;
    }
    
    public void addLobby (Lobby lobby){
        lobbies.add(lobby);
    }
    
    public List<Lobby> getLobbies (){
        return lobbies;
    }
    
    public void removeLobby (Lobby lobby){
        lobbies.remove(lobby);
    }
    
    public Player addPlayer (String id, String name){
        Player player = new Player(id, name);
        players.add(player);
        return player;
    }
    
    public Player getPlayer(String id) {
        return players.stream().filter(player -> player.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    public int getPlayerCount (){
        return players.size();
    }
    
    public Player getLocalPlayer() {
        return getPlayer(localId);
    }

    public boolean isLocalPlayer(String id) {
        return getLocalPlayer().getId().equals(id);
    }

    public boolean isPlayer (String id){
        return players.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList()).size() != 0;
    }

    public B_Pile getPile(B_PileDef def) {
        return getPlayer(def.playerId).getPileConfig().getPile(def.sort, def.pileIdx);
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }
}
