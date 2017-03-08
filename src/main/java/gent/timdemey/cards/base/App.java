package gent.timdemey.cards.base;

import java.util.Arrays;
import java.util.List;

import gent.timdemey.cards.base.cmd.Processor;
import gent.timdemey.cards.base.cmd.TransferCommand;
import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileConfig;
import gent.timdemey.cards.base.pojo.PileDef;
import gent.timdemey.cards.base.pojo.Player;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;
import gent.timdemey.cards.solitaire.SolitaireRules;
import gent.timdemey.cards.solitaire.SolitaireSorts;

public class App {

    public static void main(String[] args) {
        PileConfig pcfg = new PileConfig();
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(Card.of( "5♥","4♠", "3♦", "2♣")));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(Card.of( "5♦","4♣")));
                
        Player p = new Player("myid", "Tim", pcfg);
        List<Player> players = Arrays.asList(p);
        State state = new State(players, null, "myid");
        Processor cpu = new Processor(new SolitaireRules(), state);
        
        PileDef p1 = new PileDef("myid", SolitaireSorts.TABLEAU, 0);
        PileDef p2 = new PileDef("myid", SolitaireSorts.TABLEAU, 1);
        TransferCommand cmd = new TransferCommand(new TransferDef(p1, p2, 3));
        
        cpu.process("myid", cmd);
        
        System.out.println(state.getPile(p1));
        System.out.println(state.getPile(p2));
    }
}
