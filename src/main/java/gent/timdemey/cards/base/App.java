package gent.timdemey.cards.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gent.timdemey.cards.base.cmd.Command;
import gent.timdemey.cards.base.cmd.PickUpCommand;
import gent.timdemey.cards.base.cmd.Processor;
import gent.timdemey.cards.base.cmd.PutDownCommand;
import gent.timdemey.cards.base.cmd.TransferCommand;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.CommandWrapper;
import gent.timdemey.cards.base.pojo.PickUpDef;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileConfig;
import gent.timdemey.cards.base.pojo.PileDef;
import gent.timdemey.cards.base.pojo.Player;
import gent.timdemey.cards.base.pojo.PutDownDef;
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
        
        TransferCommand cmd1 = new TransferCommand(new TransferDef(p1, p2, 2)); // 2 cards to p2  
        cpu.process("myid", cmd1);
        
        PickUpCommand cmd2 = new PickUpCommand(new PickUpDef(p2, 2)); // pick up 2 cards from p2
        cpu.process("myid", cmd2);
        
        PickUpCommand cmd2_2 = new PickUpCommand(new PickUpDef(p2, 2)); // pick up 2 cards from p2
        
        List<Command> all = new ArrayList<>();
        all.add(cmd1);
        all.add(cmd2);
        
        Messenger m = new Messenger();
        String out = m.gson.toJson(CommandWrapper.of(all));
        System.out.println(out);
        List<CommandWrapper> read = m.gson.fromJson(out, List.class);
        System.out.println(read);
        for (CommandWrapper c : read){
            System.out.println(c);
        }
        
       /* cpu.process("myid", cmd2_2);
        
        PutDownCommand cmd3 = new PutDownCommand(new PutDownDef(p1)); // put down those 2 cards on p1
        cpu.process("myid", cmd3);
        
        System.out.println(state.getPile(p1));
        System.out.println(state.getPile(p2));*/
    }
}
