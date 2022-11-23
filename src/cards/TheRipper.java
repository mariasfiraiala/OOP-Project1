package cards;

import fileio.CardInput;

public final class TheRipper extends Minion {
    /**
     * The Ripper ability: Weak Knees, -2 attack for the affected Minion
     * @param minion affected Minion
     */
    public void weakKnees(final Minion minion) {
        minion.setAttackDamage(minion.getAttackDamage() - 2);
        if (minion.getAttackDamage() < 0) {
            minion.setAttackDamage(0);
        }
    }
    public TheRipper(final CardInput card) {
        super(card);
    }
    public TheRipper(final TheRipper card) {
        super(card);
    }
}
