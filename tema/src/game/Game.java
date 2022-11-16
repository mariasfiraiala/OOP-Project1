package game;

import cards.*;
import fileio.CardInput;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;
import table.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private Player player1, player2;
    private int seed;

    public Game(GameInput game, DecksInput decksForPlayer1, DecksInput decksForPLayer2) {
        Deck deckForPlayer1 = new Deck(decksForPlayer1.getDecks().get(game.getStartGame().getPlayerOneDeckIdx()));
        Deck deckForPlayer2 = new Deck(decksForPLayer2.getDecks().get(game.getStartGame().getPlayerTwoDeckIdx()));

        player1 = new Player(deckForPlayer1, game.getStartGame().getPlayerOneHero());
        player2 = new Player(deckForPlayer2, game.getStartGame().getPlayerTwoHero());
        seed = game.getStartGame().getShuffleSeed();
    }

    public void shuffle() {
        Collections.shuffle(this.player1.getCurrentDeck().getTotalCards(), new Random(seed));
        Collections.shuffle(this.player2.getCurrentDeck().getTotalCards(), new Random(seed));
    }
}
