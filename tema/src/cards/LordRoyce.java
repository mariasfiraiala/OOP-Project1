package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class LordRoyce extends  Hero {
    public void heroAction(ArrayList<Minion> cards) {
        int indexToFreeze = 0;
        int maxAttack = 0;
        for (Minion card : cards)
            if (card.getAttackDamage() > maxAttack) {
                maxAttack = card.getAttackDamage();
                indexToFreeze = cards.indexOf(card);
            }

        cards.get(indexToFreeze).setIsFrozen(true);
    }

    public LordRoyce(CardInput card) {
        super(card);
    }

    public LordRoyce(LordRoyce card) {
        super(card);
    }
}
