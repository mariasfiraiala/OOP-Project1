package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class EmpressThorina extends Hero {
    public void heroAction(ArrayList<Minion> cards) {
        int indexToDestroy = 0;
        int maxHealth= 0;
        for (Minion card : cards)
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                indexToDestroy = cards.indexOf(card);
            }

        cards.remove(indexToDestroy);
    }

    public EmpressThorina(CardInput card) {
        super(card);
    }

    public EmpressThorina(EmpressThorina card) {
        super(card);
    }
}
