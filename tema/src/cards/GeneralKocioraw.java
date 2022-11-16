package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public void heroAction(ArrayList<Card> cards) {
        for (Card card : cards)
            ((Minion) card).setAttackDamage(((Minion) card).getAttackDamage() + 1);
    }

    public GeneralKocioraw(CardInput card) {
        super(card);
    }
}
