package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Hero extends Card {
    private int health;
    // TODO setters and getters
    private int hasAttacked;

    public Hero(CardInput card) {
        super(card);
        this.health = 30;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHasAttacked() {
        return hasAttacked;
    }
    public void setHasAttacked(int hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
