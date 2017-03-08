package gent.timdemey.cards.base.pojo;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.cmd.Command;

public class Player {
        
    private final transient List<Command> executed;
    private final transient List<Command> intermediates;
    private transient int pDone = 0;
    
    private final String id;
    private final String name;
    private final PileConfig pilecfg;

    public Player(String id, String name, PileConfig pilecfg) {
        this.id = id;
        this.name = name;
        this.pilecfg = pilecfg;
        this.executed = new ArrayList<>();
        this.intermediates = new ArrayList<>();        
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PileConfig getPileConfig() {
        return pilecfg;
    }
    
    public List<Command> getExecuted(){
        return executed;
    }
    
    public List<Command> getIntermediates (){
        return intermediates;
    }
    
    public void setDone (int done){
        this.pDone = done;
    }
    
    public int getDone (){
        return pDone;
    }
    
    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
