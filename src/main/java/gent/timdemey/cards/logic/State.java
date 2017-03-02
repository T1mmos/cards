package gent.timdemey.cards.logic;

public class State {
    
    private final Player[] players;
    private final Player ghost;
    
    private final int localIDX;
    
    public State (Player[] players, Player ghost, int localIDX){
        this.players = players;
        this.ghost = ghost;
        this.localIDX = localIDX;
    }
    
    public Player getPlayer (int idx){
        return players[idx];
    }
    
    public Player getLocalPlayer (){
        return players[localIDX];
    }
    
    /**
     * The ghost player is the virtual player to which all cards and piles are assigned
     * that are not assigned to a real player. For example, all cards that have been played
     * do not belong to a real player anymore, and can be removed from the game or assigned
     * to common piles. Those piles belong to the ghost player.
     * @return
     */
    public Player getGhostPlayer (){
        return ghost;
    }
}
