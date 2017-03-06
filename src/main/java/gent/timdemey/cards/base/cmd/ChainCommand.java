package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;

public class ChainCommand implements Command {

    private final Command[] commands;
    
    public ChainCommand(Command... commands) {
        this.commands = commands;
    }

    @Override
    public void execute(State state) {
        for (Command cmd : commands){
            cmd.execute(state);
        }
    }

    @Override
    public void rollback(State state) {
        for (int i = commands.length - 1; i >= 0; i--){
            commands[i].rollback(state);
        }
    }

    @Override
    public boolean isAllowed(State state, Rules rules) {
        int allowCnt = 0;
        for (int i = 0 ; i < commands.length; i++){
            Command cmd = commands[i];
            if (cmd.isAllowed(state, rules)){
                cmd.execute(state);
                allowCnt++;
            } else {
                break;
            }
        }        
        for (int i = allowCnt - 1; i >= 0; i--){
            commands[i].rollback(state);
        }
        return allowCnt == commands.length;
    }

}
