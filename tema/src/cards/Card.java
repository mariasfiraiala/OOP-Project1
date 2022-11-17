package cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public int getMana() {
        return mana;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Card(CardInput card) {
        this.mana = card.getMana();
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.name = new String(card.getName());
    }

    public Card(Card card) {
        this.mana = card.getMana();
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.name = new String(card.getName());
    }

}
