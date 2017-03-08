package gent.timdemey.cards.base;

import gent.timdemey.cards.base.pojo.Card;

public class App {

    public static void main(String[] args) {
        Card.newSortedDeck().stream().forEach(System.out::println);
    }
}
