package cards;

import fileio.CardInput;

public final class TheRipper extends Minion {
    public TheRipper(final CardInput card) {
        super(card);
    }

    public TheRipper(final TheRipper card) {
        super(card);
    }

    public void weakKnees(final Minion minion) {
        minion.setAttackDamage(minion.getAttackDamage() - 2);
        if (minion.getAttackDamage() < 0) {
            minion.setAttackDamage(0);
        }
    }
}
