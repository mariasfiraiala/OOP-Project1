package table;

import cards.*;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Player {
    private Deck currentDeck;
    private Hero hero;
    private ArrayList<Minion> firstRow;
    private ArrayList<Minion> secondRow;
    private ArrayList<Card> hand;
    private int mana;

    public Player(Deck deck, CardInput card) {
        this.currentDeck = deck;
        this.hero = switch (card.getName()) {
            case "Lord Royce" -> new LordRoyce(card);
            case "Empress Thorina" -> new EmpressThorina(card);
            case "King Mudface" -> new KingMudface(card);
            case "General Kocioraw" -> new GeneralKocioraw(card);
            default -> null;
        };
        this.mana = 0;
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Minion> getFirstRow() {
        return firstRow;
    }

    public ArrayList<Minion> getSecondRow() {
        return secondRow;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setFirstRow(ArrayList<Minion> firstRow) {
        this.firstRow = firstRow;
    }

    public void setSecondRow(ArrayList<Minion> secondRow) {
        this.secondRow = secondRow;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void defrost() {
        for (Minion minion : firstRow) {
            if (minion.getFrozen() == true)
                minion.setFrozen(false);
        }

        for (Minion minion : secondRow) {
            if (minion.getFrozen() == true)
                minion.setFrozen(false);
        }
    }
}
