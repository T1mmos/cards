package gent.timdemey.cards.base.beans;

import com.google.gson.annotations.SerializedName;

public enum B_Kind {

    //@formatter:off    
    
    @SerializedName("A")  ACE   (1,  "A"),
    @SerializedName("2")  TWO   (2,  "2"),
    @SerializedName("3")  THREE (3,  "3"),
    @SerializedName("4")  FOUR  (4,  "4"),
    @SerializedName("5")  FIVE  (5,  "5"),
    @SerializedName("6")  SIX   (6,  "6"),
    @SerializedName("7")  SEVEN (7,  "7"),
    @SerializedName("8")  EIGHT (8,  "8"),
    @SerializedName("9")  NINE  (9,  "9"),
    @SerializedName("10") TEN   (10, "10"),
    @SerializedName("J")  JACK  (11, "J"),
    @SerializedName("Q")  QUEEN (12, "Q"),
    @SerializedName("K")  KING  (13, "K");
    
    //@formatter:on
    private final int value;
    private final String name;

    private B_Kind(int value, String name) {
        this.value = value;
        this.name = name;
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
    
    public String getName (){
        return name;
    }
    
    
}
