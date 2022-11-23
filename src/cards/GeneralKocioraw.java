package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class GeneralKocioraw extends Hero {
    /**
     * General Kocioraw attack, Blood Thirst, +1 attack for all cards from the affected row
     * @param cards the affected row
     */
    public void heroAction(final ArrayList<Minion> cards) {
        for (Minion card : cards) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
    }

    public GeneralKocioraw(final CardInput card) {
        super(card);
    }

    public GeneralKocioraw(final GeneralKocioraw card) {
        super(card);
    }
}
