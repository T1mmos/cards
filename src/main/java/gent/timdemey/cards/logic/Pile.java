package gent.timdemey.cards.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Pile {

    protected final List<Card> cards;

    public Pile() {
        this.cards = new ArrayList<>();
    }

    protected Pile(List<Card> cards) {
        this.cards = cards;
    }

    public int size() {
        return cards.size();
    }

    public void add(Pile pile) {
        pile.cards.stream().forEach(cards::add);
    }
    
    public void add(Card card){
        cards.add(card);
    }

    public Card peek(int idx) {
        return cards.get(idx);
    }

    public final Card peekTop() {
        return peek(size() - 1);
    }

    public final Pile remove(int idx) {
        
        List<Card> list = IntStream.range(idx, size()).mapToObj(cards::remove).collect(Collectors.toList());
        return new Pile(list);
    }

    @Override
    public final String toString() {
        return cards.stream().map(card -> card.toString()).collect(Collectors.joining(","));
    }
}
