package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class Winterfell extends Environment {
    public void environmentAction(final ArrayList<Minion> attacked,
                                  final ArrayList<Minion> attacker) {
        for (Minion card : attacked) {
            card.setIsFrozen(true);
        }
    }

    public Winterfell(final CardInput card) {
        super(card);
    }

    public Winterfell(final Winterfell card) {
        super(card);
    }
}
