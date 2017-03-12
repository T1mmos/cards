package gent.timdemey.cards.base.beans;

public final class B_PileDef {
    public final String playerId;
    public final String sort;
    public final int pileIdx;
    
    public B_PileDef (String playerId, String sort, int pileIdx){
        this.playerId = playerId;
        this.sort = sort;
        this.pileIdx = pileIdx;
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }
    
}
