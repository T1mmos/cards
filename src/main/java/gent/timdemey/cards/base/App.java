package gent.timdemey.cards.base;

import gent.timdemey.cards.base.beans.B_Card;

public class App {

    public static void main(String[] args) {
        B_Card.newSortedDeck().stream().forEach(System.out::println);
    }
}
