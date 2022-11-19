package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Winterfell extends Environment {
    public void environmentAction(ArrayList<Card> cards1, ArrayList<Card> cards2) {
        for (Card card : cards1)
            ((Minion) card).setIsFrozen(true);
    }

    public Winterfell(CardInput card) {
        super(card);
    }

    public Winterfell(Winterfell card) {
        super(card);
    }
}
