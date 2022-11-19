package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class LordRoyce extends  Hero {
    public void heroAction(ArrayList<Card> cards) {
        int indexToFreeze = 0;
        int maxAttack = 0;
        for (Card card : cards)
            if (((Minion) card).getAttackDamage() > maxAttack) {
                maxAttack = ((Minion) card).getAttackDamage();
                indexToFreeze = cards.indexOf(card);
            }

        ((Minion)(cards.get(indexToFreeze))).setIsFrozen(true);
    }

    public LordRoyce(CardInput card) {
        super(card);
    }

    public LordRoyce(LordRoyce card) {
        super(card);
    }
}
