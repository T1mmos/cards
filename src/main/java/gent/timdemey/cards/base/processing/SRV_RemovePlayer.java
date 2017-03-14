package gent.timdemey.cards.base.processing;

public class SRV_RemovePlayer extends Command {

    public final String id;

    public SRV_RemovePlayer(String id) {
        this.id = id;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
