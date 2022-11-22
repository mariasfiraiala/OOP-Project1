package table;

import cards.Card;
import cards.Deck;
import cards.EmpressThorina;
import cards.GeneralKocioraw;
import cards.Hero;
import cards.KingMudface;
import cards.LordRoyce;
import cards.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public final class Player {
    private Deck currentDeck;
    private Hero hero;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private int mana;
    private int indexFrontRow, indexBackRow;

    public Player(final Deck deck, final CardInput card, final int indexFrontRow,
                  final int indexBackRow) {
        this.currentDeck = deck;
        this.hero = switch (card.getName()) {
            case "Lord Royce" -> new LordRoyce(card);
            case "Empress Thorina" -> new EmpressThorina(card);
            case "King Mudface" -> new KingMudface(card);
            case "General Kocioraw" -> new GeneralKocioraw(card);
            default -> null;
        };
        this.mana = 1;
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

    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public void defrost(final ArrayList<Minion> firstRow, final ArrayList<Minion> secondRow) {
        for (Minion minion : firstRow) {
            if (minion.getIsFrozen()) {
                minion.setIsFrozen(false);
            }
        }

        for (Minion minion : secondRow) {
            if (minion.getIsFrozen()) {
                minion.setIsFrozen(false);
            }
        }
    }

    public void refreshAttackers(final ArrayList<Minion> firstRow,
                                 final ArrayList<Minion> secondRow) {
        for (Minion minion : firstRow) {
            if (minion.getHasAttacked()) {
                minion.setHasAttacked(false);
            }
        }

        for (Minion minion : secondRow) {
            if (minion.getHasAttacked()) {
                minion.setHasAttacked(false);
            }
        }
    }
}
