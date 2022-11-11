package game;

import fileio.CardInput;

public class Disciple extends Minion {
    private void GodsPlan(Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
    }

    public Disciple(CardInput card) {
        super(card);
    }
}
