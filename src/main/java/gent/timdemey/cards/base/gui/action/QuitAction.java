package gent.timdemey.cards.base.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class QuitAction extends AbstractAction {
    public QuitAction() {
        super("Quit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action: Quit");
    }
}
