package gent.timdemey.cards.base.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {

    private final Suit suit;
    private final Kind kind;
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
    
    public void setVisible (boolean visible){
        this.visible = visible;
    }
    
    public boolean isVisible (){
        return visible;
    }

    /**
     * Creates an list of the 52 standard cards, in random order.
     * @return
     */
    public static List<Card> newShuffledDeck () {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()){
            for (Kind kind : Kind.values()){
                cards.add(new Card (suit, kind));                
            }
        }
        Collections.shuffle(cards);
        return cards;
    }
    
    @Override
    public String toString() {
        return "" + getKind() + getSuit();
    }
}
