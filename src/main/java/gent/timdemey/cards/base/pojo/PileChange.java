package gent.timdemey.cards.base.pojo;

public final class PileChange {
    
    //@formatter:off    
        
    public final String from_playerId;
    public final String from_sort;
    public final int from_pileIdx;
    public final String to_playerId;
    public final String to_sort;
    public final int to_pileIdx;
    public final int howmany;

    public PileChange(String from_playerId, String from_sort, int from_pileIdx, String to_playerId,
            String to_sort, int to_pileIdx, int howmany) {
        this.from_playerId= from_playerId;
        this.from_sort= from_sort;
        this.from_pileIdx= from_pileIdx;
        this.to_playerId= to_playerId;
        this.to_sort= to_sort;
        this.to_pileIdx= to_pileIdx;
        this.howmany = howmany;
    }
    
    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
    
    //@formatter:on
}
