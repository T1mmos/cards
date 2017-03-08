package gent.timdemey.cards.base.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Card {

    private final Kind kind;
    private final Suit suit;
    private boolean visible;

    public Card(Suit suit, Kind kind) {
        this.suit = suit;
        this.kind = kind;
        this.visible = true;
    }

    public Suit getSuit() {
        return suit;
    }

    public Kind getKind() {
        return kind;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Creates an list of the 52 standard cards, in random order.
     * 
     * @return
     */
    public static List<Card> newShuffledDeck() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Kind kind : Kind.values()) {
                cards.add(new Card(suit, kind));
            }
        }
        Collections.shuffle(cards);
        return cards;
    }

    /**
     * Creates an list of the 52 standard cards, in standard order (hearts,
     * spades, diamonds, clubs; from ace to king).
     * 
     * @return
     */
    public static List<Card> newSortedDeck() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : new Suit[] { Suit.HEARTS, Suit.SPADES, Suit.DIAMONDS, Suit.CLUBS }) {
            for (Kind kind : Kind.values()) {
                cards.add(new Card(suit, kind));
            }
        }
        return cards;
    }

    /**
     * Quick and dirty "string to card" method. E.g. allows to map "5♥" to the
     * five of hearts Card object.
     * 
     * @param raw
     * @return
     */
    public static List<Card> of(String... raw) {

        List<Card> cards = new ArrayList<>();
        for (String s : raw) {
            Kind kind = null;
            Suit suit = null;
            String prefix;
            String suffix = null;
            for (Kind k : Kind.values()) {
                try {
                    prefix = Kind.class.getField(k.name()).getAnnotation(SerializedName.class).value();
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("Should not happen", e);
                }
                if (!s.startsWith(prefix)) {
                    continue;
                }
                kind = k;
                int prelen = prefix.length();
                suffix = s.substring(prelen);
                if (suffix.length() != 1) {
                    throw new RuntimeException("Wrong card specifier: " + s);
                }
                break;
            }
            for (Suit st : Suit.values()) {
                String stname;
                try {
                    stname = Suit.class.getField(st.name()).getAnnotation(SerializedName.class).value();
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException("Should not happen", e);
                }
                if (!stname.equals(suffix)) {
                    continue;
                }
                suit = st;
                break;
            }
            if (suit == null || kind == null) {
                throw new RuntimeException("Wrong card specifier: " + s);
            }
            cards.add(new Card(suit, kind));
        }

        return cards;
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
