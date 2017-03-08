package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.Player;
import gent.timdemey.cards.base.pojo.State;

public class Processor {

    private final Rules rules;
    private final State state;

    public Processor(Rules rules, State state) {
        this.rules = rules;
        this.state = state;
    }

    public void process(String source, Command c) {
        if (!state.isPlayer(source)) {
            throw new UnsupportedOperationException("no server developments done yet");
        }

        Player player = state.getPlayer(source);
        List<Command> tmps = player.getIntermediates();
        if (!c.isAllowed(tmps, state, rules)) { // throws ChainException
            clearIntermediates(player);
            return;
        }

        c.execute(tmps, state);

        switch (c.getType()) {
            case INTERMEDIATE:
                tmps.add(c);
                break;
            case MERGER:
                Command merged = c.merge(tmps);
                addStandalone(player, merged);
                break;
            case STANDALONE:
                addStandalone(player, c);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported type: " + c.getType());
        }
    }

    public void undo() {
        // first rollback intermediate commands
        Player p = state.getLocalPlayer();
        clearIntermediates(p);

        // rollback last
        int done = p.getDone();
        done--;
        p.getExecuted().get(done).rollback(state);
        p.setDone(done);
    }

    public void redo() {

    }

    private void clearIntermediates(Player p) {
        List<Command> tmps = p.getIntermediates();
        // clean up temps
        for (int i = tmps.size() - 1; i >= 0; i--) {
            tmps.get(i).rollback(state);
        }
        tmps.clear();
    }

    private void addStandalone(Player p, Command c) {
        if (c != null) {
            if (c.getType() != CmdType.STANDALONE) {
                throw new IllegalArgumentException(
                        "Can only add commands to execution list only accepte " + CmdType.STANDALONE);
            }
            p.getExecuted().add(c);
            p.setDone(p.getExecuted().size());
        }
        
        // in any case, clear the temps
        p.getIntermediates().clear();
    }
}
