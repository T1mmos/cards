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

import gent.timdemey.cards.base.icons.cards.CardIcon;
import gent.timdemey.cards.base.pojo.Card;
import gent.timdemey.cards.base.pojo.Pile;
import gent.timdemey.cards.base.pojo.PileConfig;
import gent.timdemey.cards.base.pojo.Player;
import gent.timdemey.cards.base.pojo.State;

public class Solitaire {
    public static void main(String[] args) {
        PileConfig pcfg = new PileConfig();
        List<Card> deck = Card.newShuffledDeck();

        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(0, 1)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(1, 3)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(3, 6)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(6, 10)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(10, 15)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(15, 21)));
        pcfg.addPile(SolitaireSorts.TABLEAU, new Pile(deck.subList(21, 28)));
        pcfg.addPile(SolitaireSorts.STOCK, new Pile(deck.subList(21, 52)));
        pcfg.addPile(SolitaireSorts.TALON, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());
        pcfg.addPile(SolitaireSorts.FOUNDATION, new Pile());

        Player player = new Player("local", "localname", pcfg);
        State state = new State(Arrays.asList(player), null, "local");

        SwingUtilities.invokeLater(() -> startUI(state));
    }

    private static void startUI(State state) {
        JFrame frame = new JFrame();
        JPanel content = buildCardPanel(state);

        frame.setContentPane(content);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JPanel buildCardPanel(State state) {
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
            Pile pile = state.getPlayer("local").getPileConfig().getPile(SolitaireSorts.TABLEAU, pidx);
            for (int i = pile.size() - 1; i >= 0; i--) {
                Card card = pile.peekCardAt(i);
                JLabel label = buildCardLabel(card);
                label.setLocation(20 + pidx * (label.getWidth() + 20), 200 + i * 30);
                panel.add(label);
            }
        }

        return panel;
    }

    private static JLabel buildCardLabel(Card card) {
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
