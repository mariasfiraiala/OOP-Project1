package table;

import fileio.CardInput;
import fileio.DecksInput;
import game.Card;
import game.Deck;
import game.Hero;

import java.util.ArrayList;

public class Player {
    private int noDecks;
    private ArrayList<Deck> totalDecks;
    private Hero hero;
    private ArrayList<Card> firstRow;
    private ArrayList<Card> secondRow;

    public Player(DecksInput decks) {
        this.noDecks = decks.getNrDecks();
        this.totalDecks = new ArrayList<Deck>(totalDecks);

        for (ArrayList<CardInput> deck: decks.getDecks()) {
            Deck myDeck = new Deck(decks.getNrCardsInDeck(), deck);
            totalDecks.add(myDeck);
        }
    }
}
