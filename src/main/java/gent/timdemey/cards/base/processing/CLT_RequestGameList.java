package gent.timdemey.cards.base.processing;

public class CLT_RequestGameList extends Command {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
