package gent.timdemey.cards.base.cmd;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

public final class Processor {
    
    private final Rules rules;
    private final State state;
    private final List<Command> cmds;
    private int pDone = 0;
    
    public Processor (Rules rules, State state){
        this.rules = rules;
        this.state = state;
        this.cmds = new ArrayList<>();
    }
    
    public void process (Command c){
        
        if (c.isAllowed(state, rules)){
            c.execute(state);
            cmds.set(pDone++, c);
            cmds.subList(pDone, cmds.size()).clear();
        } 
    }
    
        
    public void undo (){
        
    }
    
    public void undo (int position){
        
    }
}
