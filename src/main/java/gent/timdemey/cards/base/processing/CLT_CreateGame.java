package gent.timdemey.cards.base.processing;

public class CLT_CreateGame extends Command {
    public final String name;
    
    public CLT_CreateGame (String name){
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
