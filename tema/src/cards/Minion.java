package cards;

import fileio.CardInput;

public class Minion extends Card {
    private int health;
    private int attackDamage;
    private boolean isFrozen;
    private int position;
    private boolean hasAttacked;

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public boolean getFrozen() {
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

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
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


        if (this.getName().indexOf("The Ripper, Miraj, Goliath, Warden") != -1) {
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

        String isFrontRow = new String("The Ripper, Miraj, Goliath, Warden");


        if (this.getName().indexOf("The Ripper, Miraj, Goliath, Warden") != -1) {
            this.position = 1;
        }
        else {
            this.position = 0;
        }
    }
}
