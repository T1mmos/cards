package gent.timdemey.cards.base;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.processing.CLT_Connect;

public class ClientApp {

    public static void main(String[] args) {
        Socket s;
        try {
            s = new Socket(InetAddress.getLocalHost(), 666);

            Messenger m = new Messenger("client", s, msg -> handle(msg));
            m.write(new B_Message("client", new CLT_Connect("client")));
            
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
    
    private static void handle(B_Message msg){
        System.out.println(msg);
    }
}
