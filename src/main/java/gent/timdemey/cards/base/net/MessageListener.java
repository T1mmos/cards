package gent.timdemey.cards.base.net;

import gent.timdemey.cards.base.beans.B_Message;

public interface MessageListener {
    public void onReceive (B_Message msg);
}
