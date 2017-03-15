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
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.state.listeners.ServerListener;
import gent.timdemey.cards.base.state.listeners.StateListener;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ClientApp {

    public static void main(String[] args) {
        ClientApp app = new ClientApp();

        State.INSTANCE.addStateListener(new StateListener() {

            @Override
            public void onServerRemoved(Server s) {
                System.out.println("Server removed: " + s);

            }

            @Override
            public void onServerAdded(Server s) {
                System.out.println("Connected to server " + s + ", got assigned ID " + s.getLocalId());

                s.addServerListener(new ServerListener() {

                    @Override
                    public void onPlayerRemoved(Player p) {
                        System.out.println("Player removed: " + p);
                    }

                    @Override
                    public void onPlayerAdded(Player p) {
                        System.out.println("Player added: " + p);
                    }

                    @Override
                    public void onGameRemoved(Game g) {

                    }

                    @Override
                    public void onGameAdded(Game g) {

                    }
                });

                CLT_InitPlayer cmd = new CLT_InitPlayer("Tim");
                cmd.setSourceID(s.getLocalId());
                cmd.setDestinationID(s.getServerId());
                Processor.INSTANCE.process(cmd);
            }
        });

        app.start();
    }

    private void start() {

        try {
            ConnectionManager m = new ConnectionManager();
            Visitor v = new ClientVisitor(m, new SolitaireRules());
            Processor.INSTANCE.addVisitor(v);

            final Connection conn = new Connection(new Socket(InetAddress.getLocalHost(), 666));
            conn.addMessageListener(msg -> Processor.INSTANCE.process(msg.getCommand()));
            m.addBootingConnection(conn);
            conn.start();

            Thread.sleep(5000);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
