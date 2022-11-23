package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class EmpressThorina extends Hero {

    /**
     * Empress Thorina attack: Low Blow, destroys the card with the biggest health from the row
     * @param cards the affected row
     */
    public void heroAction(final ArrayList<Minion> cards) {
        int indexToDestroy = 0;
        int maxHealth = 0;
        for (Minion card : cards) {
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                indexToDestroy = cards.indexOf(card);
            }
        }

        cards.remove(indexToDestroy);
    }

    public EmpressThorina(final CardInput card) {
        super(card);
    }

    public EmpressThorina(final EmpressThorina card) {
        super(card);
    }
}
