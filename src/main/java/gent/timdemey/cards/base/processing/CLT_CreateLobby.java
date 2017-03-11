package gent.timdemey.cards.base.processing;

public class CLT_CreateLobby implements Command {
    public final String name;
    
    public CLT_CreateLobby (String name){
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
