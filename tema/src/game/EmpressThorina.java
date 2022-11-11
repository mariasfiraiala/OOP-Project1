package game;

import fileio.CardInput;

import java.util.ArrayList;

public class EmpressThorina extends Hero {
    public void heroAction(ArrayList<Card> cards) {
        int indexToDestroy = 0;
        int maxHealth= 0;
        for (Card card : cards)
            if (((Minion) card).getHealth() > maxHealth) {
                maxHealth = ((Minion) card).getHealth();
                indexToDestroy = cards.indexOf(card);
            }

        cards.remove(indexToDestroy);
    }

    public EmpressThorina(CardInput card) {
        super(card);
    }
}
