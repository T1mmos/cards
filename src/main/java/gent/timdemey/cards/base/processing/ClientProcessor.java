package gent.timdemey.cards.base.processing;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;
import gent.timdemey.cards.base.state.Player;

public class ClientProcessor implements Processor {

    private final Game state;
    private final Rules rules;
    private final List<Command> executed;
    private final List<Command> intermediates;
    private final CommandVisitor visitor;
    /**
     * Commands not allowed to execute, but coming from the server, so meaning
     * the local client is in an inconsistent state. We'll have to wait until a
     * rollback comes in in order to restore to a valid point in the past, after
     * which we'll execute all commands in the wait line to catch up with the
     * server state.
     */
    private final List<Command> wait;

    public ClientProcessor(Game state, Rules rules, Messenger messenger) {
        this.state = state;
        this.rules = rules;
        this.executed = new ArrayList<>();
        this.intermediates = new ArrayList<>();
        this.visitor = new ClientCommandVisitor(state, rules, messenger);
        this.wait = new ArrayList<>();
    }

    @Override
    public void process(B_Message msg) {
        String source = msg.getSource();
        Command cmd = msg.getCommand();
        if ("server".equals(source) || !state.isLocalPlayer(source)) {
            if (!cmd.isAllowed(intermediates, state, rules)) {
                wait.add(cmd);
                return;
            }
            cmd.execute(intermediates, state);
        //   cmd.accept(visitor);
        } else {
            Player player = state.getLocalPlayer();

            if (!cmd.isAllowed(intermediates, state, rules)) {
                clearIntermediates(player);
                return;
            }

            cmd.execute(intermediates, state);
            switch (cmd.getType()) {
                case INTERMEDIATE:
                    intermediates.add(cmd);
                    cmd.accept(visitor);
                    break;
                case MERGER:
                    Command merged = cmd.merge(intermediates);
                    merged.accept(visitor);
                    break;
                case STANDALONE:
                    addStandalone(player, cmd);
                    cmd.accept(visitor);
                    break;
                case COMMUNICATION:
                    cmd.accept(visitor);
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported type: " + cmd.getType());
            }
        }
    }

    private void clearIntermediates(Player p) {
        // clean up temps
        for (int i = intermediates.size() - 1; i >= 0; i--) {
            intermediates.get(i).rollback(state);
        }
        intermediates.clear();
    }

    private void addStandalone(Player p, Command c) {
        if (c != null) {
            if (c.getType() != CmdType.STANDALONE) {
                throw new IllegalArgumentException(
                        "Can only add commands to execution list only accepte " + CmdType.STANDALONE);
            }
            executed.add(c);
            p.setDone(executed.size());
        }

        // in any case, clear the temps
        intermediates.clear();
    }
}
