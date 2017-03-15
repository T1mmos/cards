package gent.timdemey.cards.base.processing;

public class SRV_ReturnGameList extends Command {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
