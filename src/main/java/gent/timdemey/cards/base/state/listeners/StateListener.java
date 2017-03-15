package gent.timdemey.cards.base.state.listeners;

import gent.timdemey.cards.base.state.Server;

public interface StateListener {
    public void onServerAdded (Server s);
    public void onServerRemoved (Server s);
}
