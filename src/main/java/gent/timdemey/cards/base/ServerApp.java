package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.net.Messenger;

public class ServerApp {
    public static void main(String[] args) {
        try {
            ServerSocket ssocket = new ServerSocket(666);
            while (true){
                Socket s = ssocket.accept();
                Messenger messenger = new Messenger("test", s, ServerApp::handle);
                messenger.start();
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // iD style ^^
        
    }
    
    private static void handle(B_Message msg){
        System.out.println(msg);
    }
}
