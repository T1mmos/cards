package gent.timdemey.cards.base.entities;

public enum Suit {
    HEARTS   ("♥"),
    CLUBS    ("♣"),
    DIAMONDS ("♦"),
    SPADES   ("♠");
    
    private final String friendly;
    
    private Suit (String friendly){
        this.friendly = friendly;
    }
    
    @Override
    public String toString() {
        return friendly;
    }
}
