package gent.timdemey.cards.base.net;

import java.net.InetAddress;

public interface PingbackHandler {
    public void onPingback(String id, String name, InetAddress address, int port);
}
