package game;

import cards.*;
import fileio.*;
import table.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private Player player1, player2;
    private ArrayList<Player> players;
    private int seed;
    private int startingPlayer;

    public Game(GameInput game, DecksInput decksForPlayer1, DecksInput decksForPLayer2) {
        Deck deckForPlayer1 = new Deck(decksForPlayer1.getDecks().get(game.getStartGame().getPlayerOneDeckIdx()));
        Deck deckForPlayer2 = new Deck(decksForPLayer2.getDecks().get(game.getStartGame().getPlayerTwoDeckIdx()));

        player1 = new Player(deckForPlayer1, game.getStartGame().getPlayerOneHero());
        player2 = new Player(deckForPlayer2, game.getStartGame().getPlayerTwoHero());
        seed = game.getStartGame().getShuffleSeed();
        startingPlayer = game.getStartGame().getStartingPlayer();

        if (startingPlayer == 1) {
            players.add(player1);
            players.add(player2);
        } else {
            players.add(player2);
            players.add(player1);
        }
    }

    public void shuffle() {
        Collections.shuffle(this.player1.getCurrentDeck().getTotalCards(), new Random(seed));
        Collections.shuffle(this.player2.getCurrentDeck().getTotalCards(), new Random(seed));
    }

    public int letThePlayersPlay(ArrayList<ActionsInput> actions) {
        int howManyPlayersFinishedTheirTurn = 0;
        int mana = 1;

        for (ActionsInput action : actions) {
            switch (action.getCommand()) {
                case "endPlayerTurn":
                    players.get(howManyPlayersFinishedTheirTurn).defrost();
                    ++howManyPlayersFinishedTheirTurn;
                    if (howManyPlayersFinishedTheirTurn == 2) {
                        howManyPlayersFinishedTheirTurn = 0;
                        if (mana < 10) {
                            ++mana;
                        }
                        player1.setMana(player1.getMana() + mana);
                        player2.setMana(player2.getMana() + mana);
                    }
                    break;
            }
        }
        return 1;
    }
}
