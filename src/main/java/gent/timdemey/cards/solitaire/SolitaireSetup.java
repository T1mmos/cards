package gent.timdemey.cards.solitaire;

import java.util.Arrays;
import java.util.List;

import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileConfig;
import gent.timdemey.cards.base.pojo.Player;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;

public class SolitaireSetup {
    public static State setup (){
        PileConfig pcfg = new PileConfig();
        List<Card> cards = Card.newShuffledDeck();
        
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(0, 1)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(1, 3)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(3, 6)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(6, 10)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(10, 15)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(15, 21)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(cards.subList(21, 28)));
        pcfg.addPile(SolitaireSorts.STOCK, new Pile(cards.subList(28, 52)));
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.TALON, new Pile());
        pcfg.addPile(Sorts.TEMP , new Pile());
        
        // invisible cards
        for (int i = 0 ; i < 7; i++){
            Pile p = pcfg.getPile(SolitaireSorts.TABLEAU, i);
            for (int j = 0; j < p.size() - 1 ; j++){
                p.peekCardAt(j).setVisible(false);
            }
        }
        Pile stock = pcfg.getPile(SolitaireSorts.STOCK, 0);
        for (int i = 0 ; i < stock.size(); i++){
            stock.peekCardAt(i).setVisible(false);
        }
        
        Player player = new Player("local", "Test", pcfg);
        List<Player> players = Arrays.asList(player);
        State state = new State(players, null, "local");
        return state;
    }
}
