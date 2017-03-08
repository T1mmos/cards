package gent.timdemey.cards.base.cmd;

import java.util.List;

import gent.timdemey.cards.base.logic.Rules;
import gent.timdemey.cards.base.pojo.State;
import gent.timdemey.cards.base.pojo.TransferDef;

public class AutoTransferCommand extends TransferCommand {
    
    public AutoTransferCommand(TransferDef def) {
        super(def);
    }

    @Override
    public boolean isAllowed(List<Command> prevs, State state, Rules rules) throws ChainException {
        ChainException.checkCount(prevs, this, 0);
        return rules.canAutoTransfer(state, def);
    }

}
