package game;

import fileio.CardInput;
import fileio.DecksInput;

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

    public Deck(int noCardsInDeck, ArrayList<CardInput> cards) {
        this.noCardsInDeck= noCardsInDeck;
        this.totalCards = new ArrayList<>(noCardsInDeck);

        for (CardInput card : cards) {
            Card myCard = new Card(card);
            totalCards.add(myCard);
        }
    }
}
