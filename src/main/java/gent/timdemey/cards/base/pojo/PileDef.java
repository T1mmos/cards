package gent.timdemey.cards.base.pojo;

public final class PileDef {
    public final String playerId;
    public final String sort;
    public final int pileIdx;
    
    public PileDef (String playerId, String sort, int pileIdx){
        this.playerId = playerId;
        this.sort = sort;
        this.pileIdx = pileIdx;
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
    
}
