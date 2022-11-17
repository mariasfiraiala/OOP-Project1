package table;

import cards.*;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Player {
    private Deck currentDeck;
    private Hero hero;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int mana;
    private int indexRow1, indexRow2;

    public Player(Deck deck, CardInput card, int indexRow1, int indexRow2) {
        this.currentDeck = deck;
        this.hero = switch (card.getName()) {
            case "Lord Royce" -> new LordRoyce(card);
            case "Empress Thorina" -> new EmpressThorina(card);
            case "King Mudface" -> new KingMudface(card);
            case "General Kocioraw" -> new GeneralKocioraw(card);
            default -> null;
        };
        this.mana = 0;
        this.indexRow1 = indexRow1;
        this.indexRow2 = indexRow2;
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

    public int getIndexRow1() {
        return indexRow1;
    }

    public int getIndexRow2() {
        return indexRow2;
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

    public void setIndexRow1(int indexRow1) {
        this.indexRow1 = indexRow1;
    }

    public void setIndexRow2(int indexRow2) {
        this.indexRow2 = indexRow2;
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
}
