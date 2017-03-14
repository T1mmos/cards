package gent.timdemey.cards.base.processing;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.state.Game;

public final class Processor {

    private final List<Visitor> visitors;

    public Processor() {
        this.visitors = new ArrayList<>();
    }
    
    public void addVisitor(Visitor v){
        visitors.add(v);
    }

    public void process(Command command) {
        checkNotNull(command.getSource());
        checkNotNull(command.getDestination());
        visitors.stream().forEach(command::accept);
    }
}
