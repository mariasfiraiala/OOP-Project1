package cards;

import fileio.CardInput;

public class Miraj extends Minion {
    private void SkyJack(Minion minion) {
        int tmp = this.getHealth();
        this.setHealth(minion.getHealth());
        minion.setHealth(tmp);
    }

    public Miraj(CardInput card) {
        super(card);
    }
}
