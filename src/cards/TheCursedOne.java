package cards;

import fileio.CardInput;

public final class TheCursedOne extends Minion {
    public void shapeshift(final Minion minion) {
        int tmp;
        tmp = minion.getHealth();
        minion.setHealth(minion.getAttackDamage());
        minion.setAttackDamage(tmp);
    }

    public TheCursedOne(final CardInput card) {
        super(card);
    }

    public TheCursedOne(final TheCursedOne card) {
        super(card);
    }
}
