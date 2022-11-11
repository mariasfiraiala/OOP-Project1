package game;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Hero extends Card {
    private int health = 30;
    public abstract void heroAction(ArrayList<Card> cards);

    public Hero(CardInput card) {
        super(card);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
