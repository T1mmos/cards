package gent.timdemey.cards.base.cmd;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.pojo.CommandWrapper;
import gent.timdemey.cards.base.pojo.Message;
import gent.timdemey.cards.base.pojo.PojoUtils;

public class ChainException extends RuntimeException {
    private ChainException(String msg) {
        super(msg);
    }

    public static void checkType(List<Command> cmds, Command last, int at, Class<? extends Command> expected)
            throws ChainException {

        if (!expected.isAssignableFrom(cmds.get(at).getClass())) {
            List<Command> all = new ArrayList<>(cmds);
            all.add(last);
            throw new ChainException("Following sequence cannot be chained at position " + at + ": " + all);
        }
    }

    public static void checkCount(List<Command> cmds, Command last, int expected) throws ChainException {
        if (expected != cmds.size()) {
            List<Command> all = new ArrayList<>(cmds);
            all.add(last);

            throw new ChainException("Command expected " + expected + " chains but got " + cmds.size()
                    + ", all chains:\n" + PojoUtils.pretty(cmds));
        }
    }

    public static void checkNoChaining(List<Command> cmds, Command last) throws ChainException {
        checkCount(cmds, last, 0);
    }
}
