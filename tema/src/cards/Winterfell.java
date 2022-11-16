package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Winterfell extends Environment {
    @Override
    public void environmentAction(ArrayList<Card> cards1, ArrayList<Card> cards2) {
        for (Card card : cards1)
            ((Minion) card).setFrozen(true);
    }

    public Winterfell(CardInput card) {
        super(card);
    }
}
