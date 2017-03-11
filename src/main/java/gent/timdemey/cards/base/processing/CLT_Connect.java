package gent.timdemey.cards.base.processing;

public class CLT_Connect implements Command {

    public final String name;

    public CLT_Connect(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
