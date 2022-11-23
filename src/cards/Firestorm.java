package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class Firestorm extends Environment {
    /**
     * Firestorm, -1 life for all cards from row
     * @param attacked attacked player row of cards
     * @param attacker attacker player row of cards
     */
    public void environmentAction(final ArrayList<Minion> attacked,
                                  final ArrayList<Minion> attacker) {
        for (Minion card : attacked) {
            card.setHealth(card.getHealth() - 1);
        }

        for (int i = attacked.size() - 1; i >= 0; --i) {
            if (attacked.get(i).getHealth() <= 0) {
                attacked.remove(i);
            }
        }
    }

    public Firestorm(final CardInput card) {
        super(card);
    }

    public Firestorm(final Firestorm card) {
        super(card);
    }
}
