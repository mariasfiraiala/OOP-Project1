package cards;

import fileio.CardInput;

public final class Miraj extends Minion {
    public void skyJack(final Minion minion) {
        int tmp = this.getHealth();
        this.setHealth(minion.getHealth());
        minion.setHealth(tmp);
    }

    public Miraj(final CardInput card) {
        super(card);
    }

    public Miraj(final Miraj card) {
        super(card);
    }
}
