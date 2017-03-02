package gent.timdemey.cards.logic;

public final class Processor {
    
    private final Rules rules;
    private final State state;
    private final History history;
    
    public Processor (Rules rules, State state){
        this.rules = rules;
        this.state = state;
        this.history = new History();
    }
    
    public void execute (Command comm){
        
    }
}
