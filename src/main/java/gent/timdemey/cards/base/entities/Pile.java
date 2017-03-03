package gent.timdemey.cards.base.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Pile {

    private final String name;
    protected final List<Card> cards;

    public Pile(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public Pile(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
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

    public Card peek(int idx) {
        return cards.get(idx);
    }

    public final Card peekTop() {
        return peek(size() - 1);
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
        return new Pile("temp pile (removed from " + this.name + ")", Arrays.asList(excl));
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
        return name + cards.stream().map(card -> card.toString()).collect(Collectors.joining("[", "=[", "]="));
    }
}
