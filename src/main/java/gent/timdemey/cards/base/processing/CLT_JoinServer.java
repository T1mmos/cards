package gent.timdemey.cards.base.processing;

import java.net.InetAddress;

public class CLT_JoinServer extends Command { 

    public final transient InetAddress ip;
    public final transient int port;
    public final String server_id;
    public final String player_name;
    
    public CLT_JoinServer (InetAddress ip, int port, String server_id, String player_name){
        this.ip = ip;
        this.port = port;
        this.server_id = server_id;
        this.player_name = player_name;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
