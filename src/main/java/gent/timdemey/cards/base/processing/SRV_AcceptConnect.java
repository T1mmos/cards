
package gent.timdemey.cards.base.processing;

public class SRV_AcceptConnect extends Command {
    
    public final String server_name;
    public final String server_id;
    public final String assigned_id;

    public SRV_AcceptConnect(String server_id, String assigned_id, String server_name) {
        this.server_id = server_id;
        this.assigned_id = assigned_id;
        this.server_name = server_name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
