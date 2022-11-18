package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class HeartHound extends Environment {
    public void environmentAction(ArrayList<Card> cardsLoser, ArrayList<Card> cardsWinner) {
        int indexToSteal = 0;
        int maxHealth = 0;
        for (Card card : cardsLoser)
            if (((Minion) card).getHealth() > maxHealth) {
                maxHealth = ((Minion) card).getHealth();
                indexToSteal = cardsLoser.indexOf(card);
            }

        cardsWinner.add(cardsLoser.get(indexToSteal));
        cardsLoser.remove(indexToSteal);

    }

    public HeartHound(CardInput card) {
        super(card);
    }

    public HeartHound(HeartHound card) {
        super(card);
    }
}
