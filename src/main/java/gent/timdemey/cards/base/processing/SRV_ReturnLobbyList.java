package gent.timdemey.cards.base.processing;

public class SRV_ReturnLobbyList implements Command {

    
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
