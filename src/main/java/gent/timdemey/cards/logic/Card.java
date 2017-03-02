package gent.timdemey.cards.logic;

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
    
    @Override
    public String toString() {
        return "" + getKind() + getSuit();
    }
}
