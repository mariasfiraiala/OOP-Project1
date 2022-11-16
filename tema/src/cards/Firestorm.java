package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Firestorm extends Environment {
    @Override
    public void environmentAction(ArrayList<Card> cards1, ArrayList<Card> cards2) {
        for (Card card : cards1)
            ((Minion) card).setHealth(((Minion) card).getHealth() - 1);
    }

    public Firestorm(CardInput card) {
        super(card);
    }
}
