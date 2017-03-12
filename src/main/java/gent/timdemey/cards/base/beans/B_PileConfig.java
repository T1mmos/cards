package gent.timdemey.cards.base.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gent.timdemey.cards.base.state.Sorts;

/**
 * Keeps track of all piles.
 *
 */
public final class B_PileConfig {

    private final Map<String, List<B_Pile>> piles;

    private B_PileConfig(Map<String, List<B_Pile>> piles) {
        this.piles = piles;
    }

    public static final class Builder {

        private final Map<String, List<B_Pile>> piles;

        private Builder() {
            this.piles = new HashMap<>();
        }

        public Builder addDragPile() {
            List<B_Pile> tmpPiles = new ArrayList<>();
            tmpPiles.add(new B_Pile());
            this.piles.put(Sorts.DRAG, tmpPiles);
            return this;
        }

        public Builder addPile(String sort, B_Pile pile) {
            piles.putIfAbsent(sort, new ArrayList<>());
            List<B_Pile> pilesOfSort = piles.get(sort);
            pilesOfSort.add(pile);
            return this;
        }

        public B_PileConfig create() {
            return new B_PileConfig(piles);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getPileCount(String sort) {
        return piles.get(sort).size();
    }

    public B_Pile getPile(String sort, int idx) {
        return piles.get(sort).get(idx);
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }
}
