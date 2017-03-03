package gent.timdemey.cards.base.pojo;

public class Player {

    //@formatter:off
    
    private final String id;
    private final String name;
    private final PileConfig pilecfg;

    public Player(String id, String name, PileConfig pilecfg) {
        this.id = id;
        this.name = name;
        this.pilecfg = pilecfg;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PileConfig getPileConfig() {
        return pilecfg;
    }
    
    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
    
    //@formatter:on
}
