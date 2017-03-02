package gent.timdemey.cards.logic;

public class Player {
    
    private final String name;
    private final PileConfig pilecfg;
    
    public Player (String name, PileConfig pilecfg){
        this.name = name;
        this.pilecfg = pilecfg;
    }
    
    public String getName (){
        return name;
    }
    
    public PileConfig getPileConfig (){
        return pilecfg;
    }
}
