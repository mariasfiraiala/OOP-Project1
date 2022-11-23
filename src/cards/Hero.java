package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public abstract class Hero extends Card {
    public static final int HERO_HEALTH = 30;
    private int health;
    @JsonIgnore
    private boolean hasAttacked;

    public Hero(final CardInput card) {
        super(card);
        this.health = HERO_HEALTH;
    }

    public Hero(final Hero card) {
        super(card);
        this.health = card.health;
    }

    /**
     * attack for heroes
     * @param cards the affected row
     */
    public abstract void heroAction(ArrayList<Minion> cards);

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final boolean getHasAttacked() {
        return hasAttacked;
    }
    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
