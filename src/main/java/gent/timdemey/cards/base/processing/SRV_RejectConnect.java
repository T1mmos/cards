package gent.timdemey.cards.base.processing;

public class SRV_RejectConnect extends Command {
    public final String msg;

    public SRV_RejectConnect(String msg) {
        this.msg = msg;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
