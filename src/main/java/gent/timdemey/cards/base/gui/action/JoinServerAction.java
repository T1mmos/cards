package gent.timdemey.cards.base.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import gent.timdemey.cards.base.gui.dialog.JoinServerDialog;

public class JoinServerAction extends AbstractAction {

    public JoinServerAction() {
        super("Join...");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        new JoinServerDialog().show();
    }

}
