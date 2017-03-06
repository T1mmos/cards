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
import gent.timdemey.cards.solitaire.SolitaireSetup;
import gent.timdemey.cards.solitaire.SolitaireSorts;

public class App {
    public static void main(String[] args) {
        State state = SolitaireSetup.setup();
        System.out.println(state);
    }
}
