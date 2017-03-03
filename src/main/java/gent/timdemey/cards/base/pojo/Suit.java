package gent.timdemey.cards.base.pojo;

import com.google.gson.annotations.SerializedName;

public enum Suit {

    //@formatter:off
    
    @SerializedName ("♥") HEARTS   ,
    @SerializedName ("♣") CLUBS   ,
    @SerializedName ("♦") DIAMONDS ,
    @SerializedName ("♠") SPADES  ;
    
    //@formatter:on
}
