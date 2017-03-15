package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import gent.timdemey.cards.base.beans.B_GameState;
import gent.timdemey.cards.base.net.Connection;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.processing.CLT_InitPlayer;
import gent.timdemey.cards.base.processing.ClientVisitor;
import gent.timdemey.cards.base.processing.Command;
import gent.timdemey.cards.base.processing.Processor;
import gent.timdemey.cards.base.processing.SRV_AcceptConnect;
import gent.timdemey.cards.base.processing.SRV_RejectConnect;
import gent.timdemey.cards.base.processing.Visitor;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;
import gent.timdemey.cards.base.state.Server;
import gent.timdemey.cards.base.state.State;
import gent.timdemey.cards.base.state.listeners.ServerListener;
import gent.timdemey.cards.base.state.listeners.StateListener;
import gent.timdemey.cards.solitaire.SolitaireRules;

public class ClientApp {

    private final Processor p = new Processor();

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
                
            }
        });

        app.start();
    }

    private void start() {
       
        
        try {
            InetAddress ip_addr = InetAddress.getLocalHost();
            int port = 666;
            String ip_str = ip_addr.getHostAddress();
            
            String serverId = ip_str + ":" + port;
            Socket s = new Socket(ip_addr, port);
            Messenger m = new Messenger();
            Visitor v = new ClientVisitor(p, m, new SolitaireRules());
            p.addVisitor(v);
            
            final Connection conn = new Connection(s);
            conn.addMessageListener(msg -> p.process(msg.getCommand()));
            m.addNewConnection(conn);
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
