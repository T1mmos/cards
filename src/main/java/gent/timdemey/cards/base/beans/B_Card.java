package gent.timdemey.cards.base.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class B_Card {

    private final B_Kind kind;
    private final B_Suit suit;
    private boolean visible;

    public B_Card(B_Suit suit, B_Kind kind) {
        this.suit = suit;
        this.kind = kind;
        this.visible = true;
    }

    public B_Suit getSuit() {
        return suit;
    }

    public B_Kind getKind() {
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
    public static List<B_Card> newShuffledDeck() {
        List<B_Card> cards = new ArrayList<>();
        for (B_Suit suit : B_Suit.values()) {
            for (B_Kind kind : B_Kind.values()) {
                cards.add(new B_Card(suit, kind));
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
    public static List<B_Card> newSortedDeck() {
        List<B_Card> cards = new ArrayList<>();
        for (B_Suit suit : new B_Suit[] { B_Suit.HEARTS, B_Suit.SPADES, B_Suit.DIAMONDS, B_Suit.CLUBS }) {
            for (B_Kind kind : B_Kind.values()) {
                cards.add(new B_Card(suit, kind));
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
    public static List<B_Card> of(String... raw) {

        List<B_Card> cards = new ArrayList<>();
        for (String s : raw) {
            B_Kind kind = null;
            B_Suit suit = null;
            String prefix;
            String suffix = null;
            for (B_Kind k : B_Kind.values()) {
                try {
                    prefix = B_Kind.class.getField(k.name()).getAnnotation(SerializedName.class).value();
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
            for (B_Suit st : B_Suit.values()) {
                String stname;
                try {
                    stname = B_Suit.class.getField(st.name()).getAnnotation(SerializedName.class).value();
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
            cards.add(new B_Card(suit, kind));
        }

        return cards;
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }
}
