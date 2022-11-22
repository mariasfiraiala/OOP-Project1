package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Environment extends Card {
    public abstract void environmentAction(ArrayList<Minion> attacked, ArrayList<Minion> attacker);
    public Environment(final CardInput card) {
        super(card);
    }
    public Environment(final Environment card) {
        super(card);
    }
}

