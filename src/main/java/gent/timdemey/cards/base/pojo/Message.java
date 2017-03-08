package gent.timdemey.cards.base.pojo;

import gent.timdemey.cards.base.cmd.Command;

public class Message {
    private String source;
    private Command command;
    
    public Message (String source, Command command){
        this.source = source;
        this.command = command;
    }
}
