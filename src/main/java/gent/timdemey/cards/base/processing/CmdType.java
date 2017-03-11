package gent.timdemey.cards.base.processing;

public enum CmdType {
    /**
     * A game state changing command type that cannot exist on its own, and
     * should eventually be chained to subsequent intermediate commands or a
     * merger command. A sequence of intermediate commands is ended by a merger
     * command. An intermediate command is not sent to the server.
     */
    INTERMEDIATE,
    /**
     * A game state changing command type that terminates a sequence of
     * intermediate commands. This type of commands is the end of the command
     * sequence, and such a command should convert the entire chain into a
     * single standalone command. A merger command is not sent to the server,
     * but the resulting standalone command is.
     */
    MERGER,
    /**
     * The game state changing command type for a single command unit that can
     * be executed, rolled back, and is independent from other commands. In the
     * command execution history, only standalone commands can be accepted.
     */
    STANDALONE,
    /**
     * A communication command, to exchange messages between server and client.
     * These control application flow, and are not rollbackable.
     */
    COMMUNICATION
    
}
