package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Environment extends Card {
    public abstract void environmentAction(ArrayList<Minion> attacked, ArrayList<Minion> attacker);
    public Environment(CardInput card) {
        super(card);
    }
    public Environment(Environment card) {
        super(card);
    }
}

