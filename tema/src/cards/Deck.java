package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> totalCards;
    private int noCardsInDeck;

    public ArrayList<Card> getTotalCards() {
        return totalCards;
    }

    public int getNoCardsInDeck() {
        return noCardsInDeck;
    }

    public void setTotalCards(ArrayList<Card> totalCards) {
        this.totalCards = totalCards;
    }

    public void setNoCardsInDeck(int noCardsInDeck) {
        this.noCardsInDeck = noCardsInDeck;
    }

    public void shuffleDeck(Deck deck, int seed) {

    }

    public Deck(ArrayList<CardInput> cards) {
        this.noCardsInDeck= cards.size();
        this.totalCards = new ArrayList<>(noCardsInDeck);

        for (CardInput card : cards) {
            Card myCard = new Card(card);

            if (card.getName().indexOf("Sentinel, Berserker, Goliath, Warden, The Ripper, Miraj, The Cursed One, Disciple") != -1) {
                if (card.getName().indexOf("Miraj, The Ripper, Disciple, The Cursed One") == -1) {
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
