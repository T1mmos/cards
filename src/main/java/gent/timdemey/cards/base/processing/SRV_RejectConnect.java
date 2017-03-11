package gent.timdemey.cards.base.processing;

public class SRV_RejectConnect implements Command {
    public final String msg;

    public SRV_RejectConnect(String msg) {
        this.msg = msg;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
