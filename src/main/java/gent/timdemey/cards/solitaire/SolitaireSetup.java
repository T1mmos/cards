package gent.timdemey.cards.solitaire;

import java.util.Arrays;
import java.util.List;

import gent.timdemey.cards.base.beans.B_Card;
import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileConfig;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;

public class SolitaireSetup {
    public static Game setup() {

        List<B_Card> cards = B_Card.newShuffledDeck();

        //@formatter:off
        B_PileConfig pcfg = B_PileConfig.builder()
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(0, 1)))
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(1, 3)))
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(3, 6)))
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(6, 10)))
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(10, 15)))
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(15, 21)))
            .addPile(SolitaireSorts.TABLEAU, new B_Pile(cards.subList(21, 28)))
            .addPile(SolitaireSorts.STOCK, new B_Pile(cards.subList(28, 52)))
            .addPile(SolitaireSorts.FOUNDATION, new B_Pile())
            .addPile(SolitaireSorts.FOUNDATION, new B_Pile())
            .addPile(SolitaireSorts.FOUNDATION, new B_Pile())
            .addPile(SolitaireSorts.FOUNDATION, new B_Pile())
            .addPile(SolitaireSorts.TALON, new B_Pile())
            .create();
        //@formatter:on

        // invisible cards
        for (int i = 0; i < 7; i++) {
            B_Pile p = pcfg.getPile(SolitaireSorts.TABLEAU, i);
            for (int j = 0; j < p.size() - 1; j++) {
                p.peekCardAt(j).setVisible(false);
            }
        }
        B_Pile stock = pcfg.getPile(SolitaireSorts.STOCK, 0);
        for (int i = 0; i < stock.size(); i++) {
            stock.peekCardAt(i).setVisible(false);
        }

        Player player = new Player("local", "Test", pcfg);
        List<Player> players = Arrays.asList(player);
        Game state = new Game(players, null, "local");
        return state;
    }
}
