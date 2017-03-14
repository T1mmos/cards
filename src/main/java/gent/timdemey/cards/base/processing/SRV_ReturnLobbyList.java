package gent.timdemey.cards.base.processing;

public class SRV_ReturnLobbyList extends Command {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
