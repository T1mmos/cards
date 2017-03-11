package gent.timdemey.cards.base.processing;

public class CLT_RequestLobbyList implements Command {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
