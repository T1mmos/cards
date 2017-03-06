package gent.timdemey.cards.base.pojo;

public class TransferDef {

    /**
     * The pile where the cards are currently held in.
     */
    public final PileDef from;
    /**
     * The pile where the cards are being transfered to.
     */
    public final PileDef to;
    /**
     * How many cards are being transfered.
     */
    public final int howmany;

    public TransferDef(PileDef from, PileDef to, int howmany) {
        this.from = from;
        this.to = to;
        this.howmany = howmany;
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
