package gent.timdemey.cards.base.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class StopServerAction extends AbstractAction {

    public StopServerAction() {
        super("Stop server");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action: StopServer");
    }

}
