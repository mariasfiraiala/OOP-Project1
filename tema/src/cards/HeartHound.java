package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class HeartHound extends Environment {
    public void environmentAction(ArrayList<Minion> cardsLoser, ArrayList<Minion> cardsWinner) {
        int indexToSteal = 0;
        int maxHealth = 0;
        for (Minion card : cardsLoser)
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
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
