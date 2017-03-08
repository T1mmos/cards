package gent.timdemey.cards.base.pojo;

import java.util.List;
import java.util.stream.Collectors;

import gent.timdemey.cards.base.cmd.Command;

public class CommandWrapper {
    private final Command command;

    public CommandWrapper(Command command) {
        this.command = command;
    }

    public static List<CommandWrapper> of(List<Command> cmds) {
        return cmds.stream().map(CommandWrapper::new).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return PojoUtils.pretty(this);
    }
}
