package gent.timdemey.cards.base.processing;

public interface Visitor {

    public default void visit(Command cmd) {
        throw new UnsupportedOperationException(
                "Overload the visit() method with argument of type: " + cmd.getClass().getSimpleName());
    }

    public void visit(CLT_GameCommand cmd);

    public void visit(SRV_AcceptConnect cmd);

    public void visit(CLT_RequestLobbyList cmd);
    
    public void visit(CLT_InitPlayer cmd);

}
