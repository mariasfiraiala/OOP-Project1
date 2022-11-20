package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    public void heroAction(ArrayList<Minion> cards) {
        for (Minion card : cards)
            card.setAttackDamage(card.getAttackDamage() + 1);
    }

    public GeneralKocioraw(CardInput card) {
        super(card);
    }

    public GeneralKocioraw(GeneralKocioraw card) {
        super(card);
    }
}
