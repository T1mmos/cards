package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.processing.CLT_InitPlayer;
import gent.timdemey.cards.base.processing.ClientVisitor;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;
import gent.timdemey.cards.base.state.listeners.GameListener;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ClientApp implements GameListener {

    private final Processor p = new Processor();

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        Game.INSTANCE.addListener(app);

        app.start();
    }

    private void start() {

        Socket s;
        try {
            s = new Socket(InetAddress.getLocalHost(), 666);

            Messenger m = new Messenger();
            m.addConnection("server", s);
            m.addMessageListener("server", msg -> p.process(msg.getCommand()));
            m.startConnection("server");

            Visitor v = new ClientVisitor(p, m, new SolitaireRules());
            p.addVisitor(v);

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

    @Override
    public void stateChanged(Game state) {
        System.out.println("State changed");
    }

    @Override
    public void playerAdded(Player p) {
        System.out.println("Player added: " + p);
    }

    @Override
    public void idAssigned(String id) {
        System.out.println("Got assigned ID " + id);
        Command c = new CLT_InitPlayer("Tim");
        c.setDestination("server");
        c.setSource(Game.INSTANCE.getLocalId());
        p.process(c);
    }
}
