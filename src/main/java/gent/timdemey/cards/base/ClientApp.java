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
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ClientApp {

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket(InetAddress.getLocalHost(), 666);

            Messenger m = new Messenger();
            m.addConnection("server", s);
            Processor p = new Processor();
            Visitor v = new ClientVisitor(p, m, new SolitaireRules());
            p.addVisitor(v);
            m.addListener("server", msg -> p.process(msg.getCommand()));
            
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
