package gent.timdemey.cards.base.gui.action;

import java.awt.event.ActionEvent;
import java.net.SocketException;

import javax.swing.AbstractAction;

import gent.timdemey.cards.base.gui.dialog.JoinServerDialog;
import gent.timdemey.cards.base.net.ServerPinger;

public class ShowServersAction extends AbstractAction {

    public ShowServersAction() {
        super("Join...");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ServerPinger pinger;
        try {
            pinger = new ServerPinger();
        } catch (SocketException e1) {
            e1.printStackTrace();
            return;
        }
        new JoinServerDialog(pinger).show();
    }

}
