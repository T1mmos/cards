package gent.timdemey.cards.base.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keeps track of all piles.
 *
 */
public final class PileConfig {

    private Map<String, List<Pile>> piles;

    public PileConfig() {
        this.piles = new HashMap<>();
    }

    public void addPile(String sort, Pile pile) {
        piles.putIfAbsent(sort, new ArrayList<>());
        List<Pile> pilesOfSort = piles.get(sort);
        pilesOfSort.add(pile);
    }
    
    public int getPileCount (String sort){
        return piles.get(sort).size();
    }

    public Pile getPile(String sort, int idx) {
        return piles.get(sort).get(idx);        
    }
    
    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
