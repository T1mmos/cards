package gent.timdemey.cards.base.pojo;

import com.google.gson.annotations.SerializedName;

public enum Kind {

    //@formatter:off    
    
    @SerializedName("A")  ACE   (1),
    @SerializedName("2")  TWO   (2),
    @SerializedName("3")  THREE (3),
    @SerializedName("4")  FOUR  (4),
    @SerializedName("5")  FIVE  (5),
    @SerializedName("6")  SIX   (6),
    @SerializedName("7")  SEVEN (7),
    @SerializedName("8")  EIGHT (8),
    @SerializedName("9")  NINE  (9),
    @SerializedName("10") TEN   (10),
    @SerializedName("J")  JACK  (11),
    @SerializedName("Q")  QUEEN (12),
    @SerializedName("K")  KING  (13);
    
    //@formatter:on
    private final int value;

    private Kind(int value) {
        this.value = value;
    }

    /**
     * Returns the default integer value for this kind of card. 1 for an ace, 2
     * for a two, etc., ... and king is 13. These values can be used to apply a
     * simple formula to re-assign values in the custom rules for your card
     * game. (e.g. where ace is the highest card).
     * 
     * @return
     */
    public int getValue() {
        return value;
    }
}
