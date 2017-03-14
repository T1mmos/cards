package gent.timdemey.cards.base.beans;

import gent.timdemey.cards.base.processing.Command;

public class B_Message {

    private final Command command;

    public B_Message(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return BeanUtils.pretty(this);
    }

}
