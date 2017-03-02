package gent.timdemey.cards.solishow;

import gent.timdemey.cards.logic.Card;
import gent.timdemey.cards.logic.Kind;
import gent.timdemey.cards.logic.Pile;

public class SS_EndPile implements Pile {

    @Override
    public boolean isAddable(Pile pile) {
        if (pile.size() != 1){
            return false;
        }
        Card card = pile.peek(0);
        if (size() == 0){
            return card.getKind() == Kind.ACE;
        } else {
            Card top = peekTop();            
            return top.getSuit() == card.getSuit() && card.getKind().ordinal() - top.getKind().ordinal() == 1;
        }
    }

    @Override
    public boolean isRemovable(int idx) {
        return false;
    }

    @Override
    public int size() {
        
    }

    @Override
    public void add(Pile pile) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Card peek(int idx) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pile remove(int idx) {
        // TODO Auto-generated method stub
        return null;
    }

}
