package gent.timdemey.cards.base.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class B_Pile {

    private final List<B_Card> cards;

    public B_Pile() {
        this.cards = new ArrayList<>();
    }

    public B_Pile(List<B_Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public boolean isEmpty (){
        return size() == 0;
    }
    
    public int size() {
        return cards.size();
    }

    public void add(B_Pile pile) {
        pile.cards.stream().forEach(cards::add);
    }

    public void add(B_Card card) {
        cards.add(card);
    }

    public B_Card peekCardAt(int idx) {
        return cards.get(idx);
    }

    public final B_Card peekCard () {
        return peekCardAt (size() - 1);
    }
    
    public final B_Pile peekPileAt (int idx){
        B_Pile pile = new B_Pile();
        cards.subList(idx, size()).stream().forEach(pile::add);
        return pile;
    }
    
    public final B_Pile peekPile (int count){
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
    public final B_Pile remove(int idx) {
        B_Card[] excl = new B_Card[size() - idx];
        for (int i = size() - 1; i >= idx; i--) {
            excl[i - idx] = cards.remove(i);
        }
        return new B_Pile(Arrays.asList(excl));
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
    public final B_Pile removeTop(int number) {
        return remove (size() - number);
    }

    @Override
    public final String toString() {
        return BeanUtils.small(this);
    }
}
