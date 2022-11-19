package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public abstract class Hero extends Card {
    private int health;
    @JsonIgnore
    private int hasAttacked;

    public Hero(CardInput card) {
        super(card);
        this.health = 30;
    }

    public Hero(Hero card) {
        super(card);
        this.health = card.health;
    }
    public abstract void heroAction(ArrayList<Card> cards);

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
