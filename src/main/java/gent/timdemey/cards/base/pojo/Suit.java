package gent.timdemey.cards.base.pojo;

import com.google.gson.annotations.SerializedName;

public enum Suit {

    //@formatter:off
    
    @SerializedName ("♥") HEARTS   (Color.RED,   3),
    @SerializedName ("♦") DIAMONDS (Color.RED,   2),
    @SerializedName ("♠") SPADES   (Color.BLACK, 1),
    @SerializedName ("♣") CLUBS    (Color.BLACK, 0);
    
    //@formatter:on
    private final Color color;
    private final int order;

    private Suit(Color c, int order) {
        this.color = c;
        this.order = order;
    }

    public Color getColor() {
        return color;
    }

    /**
     * The value for default suit ordering. (from high to low: hearts, diamonds,
     * spades and clubs). In the custom rules of your card game you can reassign
     * values based on the returned integer.
     * 
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     * The color of this suit. This is a convenience method to not check for two
     * suits if you can make the same decision only based on the suit color,
     * which should be shorter.
     * 
     */
    public enum Color {
        RED, BLACK
    };
}
