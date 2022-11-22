package cards;

import fileio.CardInput;

public final class Disciple extends Minion {
    public void godsPlan(final Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
    }

    public Disciple(final CardInput card) {
        super(card);
    }

    public Disciple(final Disciple card) {
        super(card);
    }
}
