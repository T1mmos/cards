package gent.timdemey.cards.base;

import java.util.ArrayList;
import java.util.Arrays;

import gent.timdemey.cards.base.cmd.Command;
import gent.timdemey.cards.base.cmd.TransferCommand;
import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.Kind;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileConfig;
import gent.timdemey.cards.base.pojo.PileDef;
import gent.timdemey.cards.base.pojo.Player;
import gent.timdemey.cards.base.pojo.Sorts;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.Suit;
import gent.timdemey.cards.base.pojo.TransferDef;
import gent.timdemey.cards.solitaire.SolitaireRules;
import gent.timdemey.cards.solitaire.SolitaireSorts;

public class App {
    public static void main(String[] args) {
        PileConfig pcfg = new PileConfig();
        pcfg.addPile(Sorts.TEMP, new Pile());
        pcfg.addPile(SolitaireSorts.TABLEAU,
                new Pile(new ArrayList<>(Arrays.asList(new Card(Suit.CLUBS, Kind.EIGHT), new Card(Suit.HEARTS, Kind.SEVEN)))));
        pcfg.addPile(SolitaireSorts.TABLEAU,
                new Pile(new ArrayList<>(Arrays.asList(new Card(Suit.SPADES, Kind.SIX), new Card(Suit.DIAMONDS, Kind.FIVE)))));

        Player p1 = new Player("tim", "Tim", pcfg);
        State state = new State(Arrays.asList(p1), null, 0);

        PileDef from = new PileDef("tim", SolitaireSorts.TABLEAU, 1);
        PileDef to = new PileDef("tim", SolitaireSorts.TABLEAU, 0);
        Command cmd = new TransferCommand(new TransferDef(from, to, 1));
        cmd.isAllowed(state, new SolitaireRules());
        System.out.println(pcfg);
        cmd.execute(state);
        System.out.println(pcfg);
    }
}
