package gent.timdemey.cards.base.pojo;

public class PutDownDef {
    public final PileDef to;
    public final int howmany;

    public PutDownDef(PileDef to, int howmany) {
        this.to = to;
        this.howmany = howmany;
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
