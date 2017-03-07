package gent.timdemey.cards.base;

import gent.timdemey.cards.base.cmd.Command;
import gent.timdemey.cards.base.cmd.TransferCommand;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.PileDef;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;
import gent.timdemey.cards.solitaire.SolitaireRules;
import gent.timdemey.cards.solitaire.SolitaireSetup;
import gent.timdemey.cards.solitaire.SolitaireSorts;

public class App {
    public static void main(String[] args) {
        State state = SolitaireSetup.setup();
        Rules rules = new SolitaireRules();
         
        Command cmd = new TransferCommand(new TransferDef(new PileDef("local", SolitaireSorts.STOCK, 0), new PileDef("local", SolitaireSorts.TALON, 0), 3));
        System.out.println(cmd.isAllowed(state, rules));
    }
}
