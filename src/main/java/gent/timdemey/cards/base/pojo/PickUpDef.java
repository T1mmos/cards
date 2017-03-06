package gent.timdemey.cards.base.pojo;

public class PickUpDef {
    public final PileDef from;
    public final int howmany;

    public PickUpDef(PileDef from, int howmany) {
        this.from = from;
        this.howmany = howmany;
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
