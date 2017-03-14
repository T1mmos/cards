package gent.timdemey.cards.base.processing;

public class CLT_RequestLobbyList extends Command {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
