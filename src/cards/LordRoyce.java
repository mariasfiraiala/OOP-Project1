package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class LordRoyce extends  Hero {
    /**
     * Lord Royce attack: Sub-Zero, freezes the card with the gratest attck damage from row
     * @param cards the affected row
     */
    public void heroAction(final ArrayList<Minion> cards) {
        int indexToFreeze = 0;
        int maxAttack = 0;
        for (Minion card : cards) {
            if (card.getAttackDamage() > maxAttack) {
                maxAttack = card.getAttackDamage();
                indexToFreeze = cards.indexOf(card);
            }
        }

        cards.get(indexToFreeze).setIsFrozen(true);
    }

    public LordRoyce(final CardInput card) {
        super(card);
    }

    public LordRoyce(final LordRoyce card) {
        super(card);
    }
}
