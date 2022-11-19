package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Winterfell extends Environment {
    public void environmentAction(ArrayList<Minion> attacked, ArrayList<Minion> attacker) {
        for (Minion card : attacked)
            card.setIsFrozen(true);
    }

    public Winterfell(CardInput card) {
        super(card);
    }

    public Winterfell(Winterfell card) {
        super(card);
    }
}
