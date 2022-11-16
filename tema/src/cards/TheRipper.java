package cards;

import fileio.CardInput;

public class TheRipper extends Minion {
    public TheRipper(CardInput card) {
        super(card);
    }

    private void WeakKnees (Minion minion) {
        minion.setHealth(minion.getHealth() - 2);
    }
}
