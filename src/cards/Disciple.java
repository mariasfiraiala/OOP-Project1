package cards;

import fileio.CardInput;

public final class Disciple extends Minion {
    /**
     * Disciple ability: God's Plan, +2 life for the affected Minion
     * @param minion affected Minion
     */
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
