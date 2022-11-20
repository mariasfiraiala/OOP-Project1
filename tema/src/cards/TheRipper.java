package cards;

import fileio.CardInput;

public class TheRipper extends Minion {
    public TheRipper(CardInput card) {
        super(card);
    }

    public TheRipper(TheRipper card) {
        super(card);
    }

    public void WeakKnees (Minion minion) {
        minion.setAttackDamage(minion.getAttackDamage() - 2);
        if (minion.getAttackDamage() < 0)
            minion.setAttackDamage(0);
    }
}
