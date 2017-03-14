package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.ServerVisitor;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.utils.StringUtils;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ServerApp {
    public static void main(String[] args) {
        try (ServerSocket ssocket = new ServerSocket(666)) {
            Messenger m = new Messenger();
            Processor p = new Processor();
            Visitor v = new ServerVisitor(p, m, new SolitaireRules());
            p.addVisitor(v);
            while (true) {
                Socket csocket = ssocket.accept();

                String unique_id = null;
                do {
                    unique_id = StringUtils.getRandomString(16);
                } while (Game.INSTANCE.isPlayer(unique_id));

                m.addConnection(unique_id, csocket);
                m.addListener(unique_id, msg -> p.process(msg.getCommand()));

                Command c = new SRV_AcceptConnect(unique_id);
                c.setSource("server");
                c.setDestination(unique_id);
                p.process(c);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
