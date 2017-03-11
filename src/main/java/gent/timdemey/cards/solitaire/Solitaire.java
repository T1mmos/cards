package gent.timdemey.cards.solitaire;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import gent.timdemey.cards.base.beans.B_Card;
import gent.timdemey.cards.base.beans.B_Pile;
import gent.timdemey.cards.base.beans.B_PileConfig;
import gent.timdemey.cards.base.icons.cards.CardIcon;
import gent.timdemey.cards.base.processing.CLT_PutDown;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;

public class Solitaire {
    public static void main(String[] args) {
        B_PileConfig pcfg = new B_PileConfig();
        List<B_Card> deck = B_Card.newShuffledDeck();

        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(0, 1)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(1, 3)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(3, 6)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(6, 10)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(10, 15)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(15, 21)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new B_Pile(deck.subList(21, 28)));
        pcfg.addPile(SolitaireSorts.STOCK, new B_Pile(deck.subList(21, 52)));
        pcfg.addPile(SolitaireSorts.TALON, new B_Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new B_Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new B_Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new B_Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new B_Pile());

        
        Player player = new Player("local", "localname", pcfg);
        Game state = new Game(Arrays.asList(player), null, "local");

        SwingUtilities.invokeLater(() -> startUI(state));
    }

    private static void startUI(Game state) {
        JFrame frame = new JFrame();
        JPanel content = buildCardPanel(state);

        frame.setContentPane(content);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JPanel buildCardPanel(Game state) {
        JPanel panel = new JPanel(null);

        JLabel cover = buildCoverLabel();
        cover.setLocation(20, 30);
        panel.add(cover);

        for (int pidx = 0; pidx < 4; pidx++) {
            JLabel empty = buildLabel(null);
            empty.setLocation(20 + (pidx + 3)* (empty.getWidth() + 20), 30);
            panel.add(empty);
        }

        for (int pidx = 0; pidx < 7; pidx++) {
            B_Pile pile = state.getPlayer("local").getPileConfig().getPile(SolitaireSorts.TABLEAU, pidx);
            for (int i = pile.size() - 1; i >= 0; i--) {
                B_Card card = pile.peekCardAt(i);
                JLabel label = buildCardLabel(card);
                label.setLocation(20 + pidx * (label.getWidth() + 20), 200 + i * 30);
                panel.add(label);
            }
        }

        return panel;
    }

    private static JLabel buildCardLabel(B_Card card) {
        return buildLabel(CardIcon.getIcon(card));
    }

    private static JLabel buildCoverLabel() {
        return buildLabel(CardIcon.getBackCover());
    }

    private static JLabel buildLabel(Icon icon) {
        JLabel label = new JLabel(icon);
        label.setSize(CardIcon.IMG_WIDTH + 4, CardIcon.IMG_HEIGHT + 4);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return label;
    }
}
