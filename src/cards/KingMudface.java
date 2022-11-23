package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class KingMudface extends Hero {
    /**
     * King Mudface attack: Earth Born, +1 health for all cards on row
     * @param cards the affected row
     */
    public void heroAction(final ArrayList<Minion> cards) {
        for (Minion card : cards) {
            card.setHealth(((Minion) card).getHealth() + 1);
        }
    }

    public KingMudface(final CardInput card) {
        super(card);
    }

    public KingMudface(final KingMudface card) {
        super(card);
    }
}
