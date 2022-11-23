package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Environment extends Card {
    /**
     * attack for Environment cards
     * @param attacked attacked player row of cards
     * @param attacker attacker player row of cards
     */
    public abstract void environmentAction(ArrayList<Minion> attacked, ArrayList<Minion> attacker);
    public Environment(final CardInput card) {
        super(card);
    }
    public Environment(final Environment card) {
        super(card);
    }
}

