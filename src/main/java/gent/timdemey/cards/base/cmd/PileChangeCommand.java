package gent.timdemey.cards.base.cmd;

import gent.timdemey.cards.base.entities.Pile;
import gent.timdemey.cards.base.entities.PileChange;
import gent.timdemey.cards.base.entities.Player;
import gent.timdemey.cards.base.entities.State;
import gent.timdemey.cards.base.logic.Rules;

public class PileChangeCommand implements Command {
    private final PileChange change;

    public PileChangeCommand(PileChange change) {        
        this.change = change;
    }

    @Override
    public void execute(State state) {
        Player player_from = state.getPlayer(change.from_playerId);
        Player player_to = state.getPlayer(change.to_playerId);
        
        Pile pile_from = player_from.getPileConfig().getPile(change.from_sort, change.from_pileIdx);
        Pile pile_to = player_to.getPileConfig().getPile(change.to_sort, change.to_pileIdx);
        
        pile_to.add(pile_from.removeTop(change.howmany));
    }
    
    @Override
    public void rollback(State state) {
        Player player_from = state.getPlayer(change.from_playerId);
        Player player_to = state.getPlayer(change.to_playerId);
        
        Pile pile_from = player_from.getPileConfig().getPile(change.from_sort, change.from_pileIdx);
        Pile pile_to = player_to.getPileConfig().getPile(change.from_sort, change.to_pileIdx);
        
        pile_from.add(pile_to.removeTop(change.howmany));
    }

    @Override
    public boolean isAllowed(State state, Rules rules) {
        return rules.isAllowed(state, this);
    }
}
