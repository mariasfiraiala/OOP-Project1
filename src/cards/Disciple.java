package cards;

import fileio.CardInput;

public class Disciple extends Minion {
    public void GodsPlan(Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
    }

    public Disciple(CardInput card) {
        super(card);
    }

    public Disciple(Disciple card) {
        super(card);
    }
}
