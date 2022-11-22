package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    @JsonIgnore
    private String type;

    public final int getMana() {
        return mana;
    }

    public final String getDescription() {
        return description;
    }

    public final ArrayList<String> getColors() {
        return colors;
    }

    public final String getName() {
        return name;
    }

    public final String getType() {
        return type;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.name = new String(card.getName());

        String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper, Miraj,"
                + "The Cursed One, Disciple");
        String isEnvironment = new String("Firestorm, Winterfell, Heart Hound");

        if (isMinion.indexOf(card.getName()) != -1) {
            this.type =  new String("Minion");
        } else if (isEnvironment.indexOf(card.getName()) != -1) {
            this.type = new String("Environment");
        } else {
            this.type = new String("Hero");
        }
    }

    public Card(final Card card) {
        this.mana = card.getMana();
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.name = new String(card.getName());

        String isMinion = new String("Sentinel, Berserker, Goliath, Warden, The Ripper, Miraj,"
                + "The Cursed One, Disciple");
        String isEnvironment = new String("Firestorm, Winterfell, Heart Hound");

        if (isMinion.indexOf(card.getName()) != -1) {
            this.type =  new String("Minion");
        } else if (isEnvironment.indexOf(card.getName()) != -1) {
            this.type = new String("Environment");
        } else {
            this.type = new String("Hero");
        }
    }

    public final String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
