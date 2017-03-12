package gent.timdemey.cards.base.processing;

import java.util.ArrayList;
import java.util.List;

import gent.timdemey.cards.base.beans.B_Message;
import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.net.Messenger;
import gent.timdemey.cards.base.state.Game;

public class ClientProcessor implements Processor {

    private final Game game;
    private final Rules rules;
    private final List<Command> executed;
    private final List<Command> intermediates;
    private final Visitor visitor;
    /**
     * Commands not allowed to execute, but coming from the server, so meaning
     * the local client is in an inconsistent state. We'll have to wait until a
     * rollback comes in in order to restore to a valid point in the past, after
     * which we'll execute all commands in the wait line to catch up with the
     * server state.
     */
    private final List<Command> wait;

    public ClientProcessor(Game game, Rules rules, Messenger messenger) {
        this.game = game;
        this.rules = rules;
        this.executed = new ArrayList<>();
        this.intermediates = new ArrayList<>();
        this.visitor = new ClientVisitor(game, rules, messenger);
        this.wait = new ArrayList<>();
    }

    @Override
    public void process(B_Message msg) {
        String source = msg.getSource();

    }

}
