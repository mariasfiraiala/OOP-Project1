package cards;

import fileio.CardInput;

public class Minion extends Card {
    private int health;
    private int attackDamage;
    private boolean isFrozen;

    // TODO setters getters
    private int position;
    private int haveAttacked;

    public int getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public boolean getFrozen() {
        return isFrozen;
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

    public Minion(CardInput card) {
        super(card);
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
        this.isFrozen = false;
    }
}
