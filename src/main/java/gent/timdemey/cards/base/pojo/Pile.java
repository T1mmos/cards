package gent.timdemey.cards.base.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Pile {

    protected final List<Card> cards;

    public Pile() {
        this.cards = new ArrayList<>();
    }

    public Pile(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isEmpty (){
        return size() == 0;
    }
    
    public int size() {
        return cards.size();
    }

    public void add(Pile pile) {
        pile.cards.stream().forEach(cards::add);
    }

    public void add(Card card) {
        cards.add(card);
    }

    public Card peekCardAt(int idx) {
        return cards.get(idx);
    }

    public final Card peekCard () {
        return peekCardAt (size() - 1);
    }
    
    public final Pile peekPileAt (int idx){
        Pile pile = new Pile();
        cards.subList(idx, size()).stream().forEach(pile::add);
        return pile;
    }
    
    public final Pile peekPile (int count){
        return peekPileAt(size() - count);
    }

    /**
     * Removes all cards from this pile with an index higher than or equal to
     * the given index. A new pile is returned where the bottom card (index 0)
     * will be the card that had the given index in this pile.
     * 
     * @param idx
     * @return
     */
    public final Pile remove(int idx) {
        Card[] excl = new Card[size() - idx];
        for (int i = size() - 1; i >= idx; i--) {
            excl[i - idx] = cards.remove(i);
        }
        return new Pile(Arrays.asList(excl));
    }
    
    public boolean isVisible (int count){
        return isVisibleAt(size() - count);
    }
    
    public boolean isVisibleAt (int index){
        for (int i = index; i < size(); i++){
            if (!peekCardAt(i).isVisible()){
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the given number of cards from this pile, starting with the top card.
     * 
     * @param idx
     * @return
     */
    public final Pile removeTop(int number) {
        return remove (size() - number);
    }

    @Override
    public final String toString() {
        return PojoUtils.pretty(this);
    }
}
