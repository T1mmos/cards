package gent.timdemey.cards.base.beans;

import gent.timdemey.cards.base.processing.Command;

public class B_Message {
    private String source;
    private String destination;
    private Command command;

    public B_Message(String source, Command command) {
        this.source = source;
        this.command = command;
    }

    public String getSource() {
        return source;
    }

    public Command getCommand() {
        return command;
    }
    
    
}
