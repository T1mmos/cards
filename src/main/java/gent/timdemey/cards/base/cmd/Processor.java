package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

public final class Processor {
    
    private final Rules rules;
    private final State state;
    private final History history;
    
    public Processor (Rules rules, State state){
        this.rules = rules;
        this.state = state;
        this.history = new History();
    }
    
}
