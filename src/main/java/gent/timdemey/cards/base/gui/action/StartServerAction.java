package gent.timdemey.cards.base.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class StartServerAction extends AbstractAction {

    public StartServerAction() {
        super("Start server...");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action: StartServer");
    }

}
