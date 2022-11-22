package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

public class Minion extends Card {
    private int health;
    private int attackDamage;
    @JsonIgnore
    private boolean isFrozen;
    @JsonIgnore
    private int position;
    @JsonIgnore
    private boolean hasAttacked;

    public final int getHealth() {
        return health;
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final boolean getIsFrozen() {
        return isFrozen;
    }

    public final int getPosition() {
        return position;
    }

    public final boolean getHasAttacked() {
        return hasAttacked;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final void setIsFrozen(final boolean frozen) {
        this.isFrozen = frozen;
    }

    public final void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public Minion(final CardInput card) {
        super(card);
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
        this.isFrozen = false;
        this.hasAttacked = false;

        String isFrontRow = new String("The Ripper, Miraj, Goliath, Warden");


        if (isFrontRow.indexOf(card.getName()) != -1) {
            this.position = 1;
        } else {
            this.position = 0;
        }
    }

    public Minion(final Minion card) {
        super(card);
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
        this.isFrozen = false;
        this.hasAttacked = false;
        this.position = card.getPosition();
    }
}
