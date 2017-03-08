package gent.timdemey.cards.base.pojo;

/**
 * Put down all cards from the TEMP pile onto a specified pile.
 * @author TDME
 *
 */
public class PutDownDef {
    public final PileDef to;

    public PutDownDef(PileDef to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
