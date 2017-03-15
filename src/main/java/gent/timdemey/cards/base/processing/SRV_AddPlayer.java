package gent.timdemey.cards.base.processing;

public class SRV_AddPlayer extends Command {

    public final String id;
    public final String name;
    
    public SRV_AddPlayer (String id, String name){
        this.id = id;
        this.name = name;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
