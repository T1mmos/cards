package gent.timdemey.cards.base.state;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.state.listeners.StateListener;

public enum State {
    INSTANCE;

    private final List<Server> servers;
    private final List<StateListener> stateListeners;

    private State() {
        this.servers = new ArrayList<>();
        this.stateListeners = new ArrayList<>();
    }

    public Server getServer(String id) {
        return servers.stream().filter(s -> s.getServerId().equals(id)).findFirst().get();
    }

    public void addServer(Server server) {
        servers.add(server);
        stateListeners.forEach(l -> l.onServerAdded(server));
    }

    public void removeServer(Server server) {
        servers.remove(server);
        stateListeners.forEach(l -> l.onServerRemoved(server));
    }
    
    public void addStateListener (StateListener listener){
        stateListeners.add(listener);
    }
}
