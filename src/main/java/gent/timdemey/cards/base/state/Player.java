package gent.timdemey.cards.base.state;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.beans.B_PileConfig;
import gent.timdemey.cards.base.beans.BeanUtils;
import gent.timdemey.cards.base.state.listeners.PlayerListener;

public class Player {

    private final transient List<PlayerListener> lstrs;
    private transient int pDone = 0;
    private transient B_PileConfig pilecfg = null;

    private final String id;
    private String name;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
        this.lstrs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        lstrs.stream().forEach(l -> l.onNameChanged(this));
    }

    public void setPileConfig(B_PileConfig cfg) {
        this.pilecfg = cfg;
        lstrs.stream().forEach(l -> l.onPileConfigChanged(this));
    }

    public B_PileConfig getPileConfig() {
        return pilecfg;
    }

    public void setDone(int done) {
        this.pDone = done;
    }

    public int getDone() {
        return pDone;
    }

    public void addListener(PlayerListener l) {
        lstrs.add(l);
    }

    @Override
    public String toString() {
        return BeanUtils.small(this);
    }
}
