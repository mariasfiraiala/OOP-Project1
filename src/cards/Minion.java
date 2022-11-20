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

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public int getPosition() {
        return position;
    }

    public boolean getHasAttacked() {
        return hasAttacked;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setIsFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public Minion(CardInput card) {
        super(card);
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
        this.isFrozen = false;
        this.hasAttacked = false;

        String isFrontRow = new String("The Ripper, Miraj, Goliath, Warden");


        if (isFrontRow.indexOf(card.getName()) != -1) {
            this.position = 1;
        }
        else {
            this.position = 0;
        }
    }

    public Minion(Minion card) {
        super(card);
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
        this.isFrozen = false;
        this.hasAttacked = false;
        this.position = card.getPosition();
    }
}
