package table;

import cards.*;
import fileio.CardInput;

import java.util.ArrayList;

public class Player {
    private Deck currentDeck;
    private Hero hero;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int mana;
    private int indexFrontRow, indexBackRow;

    public Player(Deck deck, CardInput card, int indexFrontRow, int indexBackRow) {
        this.currentDeck = deck;
        this.hero = switch (card.getName()) {
            case "Lord Royce" -> new LordRoyce(card);
            case "Empress Thorina" -> new EmpressThorina(card);
            case "King Mudface" -> new KingMudface(card);
            case "General Kocioraw" -> new GeneralKocioraw(card);
            default -> null;
        };
        this.mana = 0;
        this.indexFrontRow = indexFrontRow;
        this.indexBackRow = indexBackRow;
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getMana() {
        return mana;
    }

    public int getIndexFrontRow() {
        return indexFrontRow;
    }

    public int getIndexBackRow() {
        return indexBackRow;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setIndexFrontRow(int indexFrontRow) {
        this.indexFrontRow = indexFrontRow;
    }

    public void setIndexBackRow(int indexBackRow) {
        this.indexBackRow = indexBackRow;
    }

    public void defrost(ArrayList<Minion> firstRow, ArrayList<Minion> secondRow) {
        for (Minion minion : firstRow) {
            if (minion.getFrozen() == true)
                minion.setFrozen(false);
        }

        for (Minion minion : secondRow) {
            if (minion.getFrozen() == true)
                minion.setFrozen(false);
        }
    }

    public void refreshAttackers(ArrayList<Minion> firstRow, ArrayList<Minion> secondRow) {
        for (Minion minion : firstRow) {
            if (minion.getHasAttacked() == true)
                minion.setHasAttacked(false);
        }

        for (Minion minion : secondRow) {
            if (minion.getHasAttacked() == true)
                minion.setHasAttacked(false);
        }
    }
}
