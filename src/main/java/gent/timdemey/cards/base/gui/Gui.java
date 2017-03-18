package gent.timdemey.cards.base.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.alee.laf.WebLookAndFeel;

import gent.timdemey.cards.base.gui.action.ShowServersAction;
import gent.timdemey.cards.base.gui.action.QuitAction;
import gent.timdemey.cards.base.gui.action.StartServerAction;
import gent.timdemey.cards.base.gui.action.StopServerAction;
import gent.timdemey.cards.base.processing.ClientVisitor;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class Gui {
    public static void main(String[] args) {
        WebLookAndFeel.install();

        Visitor v = new ClientVisitor(new SolitaireRules());
        Processor.INSTANCE.addVisitor(v);
        
        SwingUtilities.invokeLater(() -> runUI());
    }

    private static void runUI() {
        JFrame frame = new JFrame();
        frame.setJMenuBar(buildMenuBar());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    private static JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();

        // Game
        {
            JMenu file = new JMenu("Game");
            { // Join
                JMenuItem join = new JMenuItem(new ShowServersAction());
                file.add(join);
            }
            file.addSeparator();            
            { // Start & Stop server
                JMenuItem startsrv = new JMenuItem(new StartServerAction());
                file.add(startsrv);

                JMenuItem stopsrv = new JMenuItem(new StopServerAction());
                file.add(stopsrv);
            }
            file.addSeparator();
            { // Quit
                JMenuItem join = new JMenuItem(new QuitAction());
                file.add(join);
            }
            
            bar.add(file);
        }
        return bar;
    }
}
