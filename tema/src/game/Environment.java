package game;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Environment extends Card {
    public abstract void environmentAction(ArrayList<Card> cards1, ArrayList<Card> cards2);

    public Environment(CardInput card) {
        super(card);
    }
}

