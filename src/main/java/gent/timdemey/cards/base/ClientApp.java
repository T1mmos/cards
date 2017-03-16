package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import gent.timdemey.cards.base.net.Connection;
import gent.timdemey.cards.base.net.ConnectionManager;
import gent.timdemey.cards.base.processing.CLT_InitPlayer;
import gent.timdemey.cards.base.processing.ClientVisitor;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.state.listeners.StateListener;
import gent.timdemey.cards.base.utils.EventLogger;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ClientApp {

    public static void main(String[] args) {
        ClientApp app = new ClientApp();

        State.INSTANCE.addStateListener(new StateListener() {

            @Override
            public void onServerRemoved(Server s) {
               
            }

            @Override
            public void onServerAdded(Server s) {
                new CLT_InitPlayer("Tim").setSourceID(s.getLocalId()).unicast(s.getServerId());
            }
        });

        app.start();
    }

    private void start() {

        try {
            EventLogger.install();
            Visitor v = new ClientVisitor(new SolitaireRules());
            Processor.INSTANCE.addVisitor(v);

            final Connection conn = ConnectionManager.newBootingConnection(new Socket(InetAddress.getLocalHost(), 666));
            conn.addMessageListener(msg -> Processor.INSTANCE.process(msg.getCommand()));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
