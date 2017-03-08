package gent.timdemey.cards.base.cmd;

public enum CmdType {
    /**
     * A command that cannot exist on its own, and should eventually be chained
     * to subsequent intermediate commands or a merger command. A sequence of 
     * intermediate commands is ended by a merger command.
     */
    INTERMEDIATE,
    /**
     * A commands that terminates a sequence of intermediate commands. This type of 
     * commands is the end of the command sequence, and such a command
     * should convert the entire chain into a single standalone command.
     */
    MERGER, 
    /**
     * The command type for a single command unit that can be executed, rolled back, and
     * is independent from other commands. In the command execution history, only 
     * standalone commands can be accepted. 
     */
    STANDALONE
}
