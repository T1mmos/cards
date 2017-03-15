package gent.timdemey.cards.base.processing;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

public enum Processor {

    INSTANCE;
    
    private final List<Visitor> visitors;

    private Processor() {
        this.visitors = new ArrayList<>();
    }
    
    public void addVisitor(Visitor v){
        visitors.add(v);
    }

    public void process(Command command) {
        System.out.println(command);
        checkNotNull(command.getSource());
        checkNotNull(command.getDestination());
        visitors.stream().forEach(command::accept);
    }
}
