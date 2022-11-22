package cards;

import fileio.CardInput;

import java.util.ArrayList;

public final class Deck {
    private ArrayList<Card> totalCards;
    private int noCardsInDeck;

    public ArrayList<Card> getTotalCards() {
        return totalCards;
    }

    public Deck(final ArrayList<CardInput> cards) {
        this.noCardsInDeck = cards.size();
        this.totalCards = new ArrayList<>(noCardsInDeck);

        for (CardInput card : cards) {
            String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper,"
                    + "Miraj, The Cursed One, Disciple");
            String isSpecialMinion = new String("Miraj, The Ripper, Disciple, The Cursed One");

            if (isMinion.indexOf(card.getName()) != -1) {
                if (isSpecialMinion.indexOf(card.getName()) == -1) {
                    Minion minion = new Minion(card);
                    totalCards.add(minion);
                } else if (card.getName().compareTo("Miraj") == 0) {
                    Miraj miraj = new Miraj(card);
                    totalCards.add(miraj);
                } else if (card.getName().compareTo("The Ripper") == 0) {
                    TheRipper theRipper = new TheRipper(card);
                    totalCards.add(theRipper);
                } else if (card.getName().compareTo("Disciple") == 0) {
                    Disciple disciple = new Disciple(card);
                    totalCards.add(disciple);
                } else {
                    TheCursedOne theCursedOne = new TheCursedOne(card);
                    totalCards.add(theCursedOne);
                }
            } else {
                if (card.getName().compareTo("Firestorm") == 0) {
                    Firestorm firestorm = new Firestorm(card);
                    totalCards.add(firestorm);
                } else if (card.getName().compareTo("Winterfell") == 0) {
                    Winterfell winterfell = new Winterfell(card);
                    totalCards.add(winterfell);
                } else {
                    HeartHound heartHound = new HeartHound(card);
                    totalCards.add(heartHound);
                }
            }
        }
    }
}
