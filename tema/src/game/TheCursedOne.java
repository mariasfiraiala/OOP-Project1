package game;

import fileio.CardInput;

public class TheCursedOne extends Minion {
    private void Shapeshift(Minion minion) {
        int tmp;
        tmp = minion.getHealth();
        minion.setHealth(minion.getAttackDamage());
        minion.setAttackDamage(tmp);
    }

    public TheCursedOne(CardInput card) {
        super(card);
    }
}
