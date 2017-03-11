package gent.timdemey.cards.base.processing;

public interface Visitor {

    public void visit(ALL_TransferCommand cmd);

    public void visit(ALL_GameCommand cmd);

    public void visit(CLT_CreateLobby cmd);

    public void visit(CLT_Connect cmd);
    
    public void visit(SRV_RejectConnect cmd);

    public void visit(SRV_AcceptConnect cmd);
    
    public void visit(CLT_RequestLobbyList cmd);
    
    public void visit(SRV_ReturnLobbyList cmd);
}
