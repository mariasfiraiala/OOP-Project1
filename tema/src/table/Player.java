package table;

import cards.*;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Player {
    private Deck currentDeck;
    private Hero hero;
    private ArrayList<Card> firstRow;
    private ArrayList<Card> secondRow;
    private ArrayList<Card> hand;

    public Player(Deck deck, CardInput card) {
        this.currentDeck = deck;
        this.hero = switch (card.getName()) {
            case "Lord Royce" -> new LordRoyce(card);
            case "Empress Thorina" -> new EmpressThorina(card);
            case "King Mudface" -> new KingMudface(card);
            case "General Kocioraw" -> new GeneralKocioraw(card);
            default -> null;
        };
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Card> getFirstRow() {
        return firstRow;
    }

    public ArrayList<Card> getSecondRow() {
        return secondRow;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setFirstRow(ArrayList<Card> firstRow) {
        this.firstRow = firstRow;
    }

    public void setSecondRow(ArrayList<Card> secondRow) {
        this.secondRow = secondRow;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}
