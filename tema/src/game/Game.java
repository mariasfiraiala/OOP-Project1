package game;

import cards.*;
import fileio.*;
import table.Commands;
import table.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private Player player1, player2;
    private ArrayList<Player> players = new ArrayList<Player>(2);
    private int seed;
    private int startingPlayer;

    private ArrayList<Minion>[] table = new ArrayList[4];

    public Game(GameInput game, DecksInput decksForPlayer1, DecksInput decksForPLayer2) {
        Deck deckForPlayer1 = new Deck(decksForPlayer1.getDecks().get(game.getStartGame().getPlayerOneDeckIdx()));
        Deck deckForPlayer2 = new Deck(decksForPLayer2.getDecks().get(game.getStartGame().getPlayerTwoDeckIdx()));

        player1 = new Player(deckForPlayer1, game.getStartGame().getPlayerOneHero(), 2, 3);
        player2 = new Player(deckForPlayer2, game.getStartGame().getPlayerTwoHero(), 1, 0);
        seed = game.getStartGame().getShuffleSeed();
        startingPlayer = game.getStartGame().getStartingPlayer();

        if (startingPlayer == 1) {
            players.add(player1);
            players.add(player2);
        } else {
            players.add(player2);
            players.add(player1);
        }

        for (int i = 0; i < 4; ++i) {
            table[i] = new ArrayList<Minion>();
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
            // if we start a new round, we give each player a card
            if (howManyPlayersFinishedTheirTurn == 0 && player1.getCurrentDeck().getTotalCards().size() != 0 && player2.getCurrentDeck().getTotalCards().size() != 0) {
                player1.getHand().add(player1.getCurrentDeck().getTotalCards().remove(0));
                player2.getHand().add(player2.getCurrentDeck().getTotalCards().remove(0));
            }

            switch (action.getCommand()) {
                case "endPlayerTurn":
                    // before getting to the next turn, defrost frozen cards
                    players.get(howManyPlayersFinishedTheirTurn).defrost(table[players.get(howManyPlayersFinishedTheirTurn).getIndexFrontRow()], table[players.get(howManyPlayersFinishedTheirTurn).getIndexBackRow()]);
                    players.get(howManyPlayersFinishedTheirTurn).refreshAttackers(table[players.get(howManyPlayersFinishedTheirTurn).getIndexFrontRow()], table[players.get(howManyPlayersFinishedTheirTurn).getIndexBackRow()]);
                    ++howManyPlayersFinishedTheirTurn;

                    // end of a round, prepare new one
                    if (howManyPlayersFinishedTheirTurn == 2) {
                        howManyPlayersFinishedTheirTurn = 0;
                        if (mana < 10) {
                            ++mana;
                        }
                        player1.setMana(player1.getMana() + mana);
                        player2.setMana(player2.getMana() + mana);
                    }
                    break;
                case "placeCard":
                    Commands.RegularCommands placeCard = new Commands.RegularCommands();
                    placeCard.applyPlaceCard(action.getHandIdx(), players.get(howManyPlayersFinishedTheirTurn), table);
                    break;
                case "cardUsesAttack":
                    Commands.RegularCommands cardUsesAttack = new Commands.RegularCommands();
                    cardUsesAttack.applyCardUsesAttack(action.getCardAttacker(), action.getCardAttacked(), players.get(howManyPlayersFinishedTheirTurn), players.get((howManyPlayersFinishedTheirTurn + 1) % 2), table);
                    break;
            }
        }
        return 1;
    }
}
