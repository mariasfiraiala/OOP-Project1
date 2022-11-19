package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Firestorm extends Environment {
    public void environmentAction(ArrayList<Minion> attacked, ArrayList<Minion> attacker) {
        for (Minion card : attacked) {
            card.setHealth(card.getHealth() - 1);
            if (card.getHealth() <= 0) {
                attacked.remove(card);
            }
        }
    }

    public Firestorm(CardInput card) {
        super(card);
    }

    public Firestorm(Firestorm card) {
        super(card);
    }
}
