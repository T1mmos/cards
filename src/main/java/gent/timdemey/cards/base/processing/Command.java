package gent.timdemey.cards.base.processing;

/**
 * A command is an atomic piece that unconditionally executes a specific step,
 * and has the ability to undo that step as well.
 * 
 */
public interface Command {

    public void accept(Visitor visitor);

}
