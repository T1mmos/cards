package gent.timdemey.cards.base;

import java.util.List;

import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileChange;
import gent.timdemey.cards.base.pojo.PileConfig;
import gent.timdemey.cards.base.pojo.Player;

public class App {
    public static void main(String[] args) {
        List<Card> cards = Card.newShuffledDeck();

        PileConfig pcfg = new PileConfig();
        pcfg.addPile("middle", new Pile(cards.subList(0, 13)));
        pcfg.addPile("middle", new Pile(cards.subList(13, 26)));
        pcfg.addPile("middle", new Pile(cards.subList(26, 39)));
        pcfg.addPile("middle", new Pile(cards.subList(39, 52)));

        Player p = new Player("01:02:01", "Tim", pcfg);

        System.out.println(p);

        PileChange change = new PileChange("01:02:01", "middle", 2, "AB:CD:EF", "middle", 1, 3);
        System.out.println(change);
    }
}
