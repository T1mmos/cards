
package gent.timdemey.cards.base.processing;

public class SRV_AcceptConnect extends Command {
    public final String assigned_id;

    public SRV_AcceptConnect(String id) {
        this.assigned_id = id;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
