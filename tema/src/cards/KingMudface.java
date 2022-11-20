package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class KingMudface extends Hero {
    public void heroAction(ArrayList<Minion> cards) {
        for (Minion card : cards)
            card.setHealth(((Minion) card).getHealth() + 1);
    }

    public KingMudface(CardInput card) {
        super(card);
    }

    public KingMudface(KingMudface card) {
        super(card);
    }
}
