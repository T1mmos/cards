package gent.timdemey.cards.base.processing;

import gent.timdemey.cards.base.beans.BeanUtils;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;

/**
 * A command is an atomic piece that unconditionally executes a specific step,
 * and has the ability to undo that step as well.
 * 
 */
public abstract class Command {

    private String source_id;
    private String destination_id;
    
    public String getSource (){
        return source_id;
    }
    
    public String getDestination (){
        return destination_id;
    }
    
    public void setSource(String source_id){
        this.source_id = source_id;
    }
    
    public void setDestination (String destination_id){
        this.destination_id = destination_id;
    }
    
    public abstract void accept(Visitor visitor);
     
    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }
}
