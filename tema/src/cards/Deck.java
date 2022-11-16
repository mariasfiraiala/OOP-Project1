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
            totalCards.add(myCard);
        }
    }
}
