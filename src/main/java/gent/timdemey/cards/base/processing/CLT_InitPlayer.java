package gent.timdemey.cards.base.processing;

public class CLT_InitPlayer extends Command {
    public final String name;

    public CLT_InitPlayer(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
