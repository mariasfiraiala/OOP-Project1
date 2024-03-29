package game;

import cards.Minion;
import cards.Deck;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.DecksInput;
import fileio.GameInput;
import table.Commands;
import table.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {
    public static final int MAX_MANA = 10;
    public static final int MAX_ROWS = 4;

    public static final int INDEX_BACK_ROW = 3;
    private Player player1, player2;
    private ArrayList<Player> players = new ArrayList<Player>(2);
    private int seed;
    private int startingPlayer;

    private ArrayList<Minion>[] table = new ArrayList[MAX_ROWS];

    public Game(final GameInput game, final DecksInput decksForPlayer1,
                final DecksInput decksForPLayer2) {
        Deck deckForPlayer1 = new Deck(decksForPlayer1.getDecks().get(game.getStartGame().
                getPlayerOneDeckIdx()));
        Deck deckForPlayer2 = new Deck(decksForPLayer2.getDecks().get(game.getStartGame().
                getPlayerTwoDeckIdx()));

        player1 = new Player(deckForPlayer1, game.getStartGame().getPlayerOneHero(), 2,
                INDEX_BACK_ROW);
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

        for (int i = 0; i < MAX_ROWS; ++i) {
            table[i] = new ArrayList<Minion>();
        }
    }

    /**
     * shuffles the decks with the given seed
     */
    public void shuffle() {
        Collections.shuffle(this.player1.getCurrentDeck().getTotalCards(), new Random(seed));
        Collections.shuffle(this.player2.getCurrentDeck().getTotalCards(), new Random(seed));
    }

    /**
     * applies commands for the given game
     * @param actions array of actions to be completed
     * @param output writes in file
     * @return the winning player of the game, or 0 otherwise
     */
    public int letThePlayersPlay(final ArrayList<ActionsInput> actions, final ArrayNode output) {
        int howManyPlayersFinishedTheirTurn = 0;
        if (player1.getCurrentDeck().getTotalCards().size() != 0 && player2.getCurrentDeck().
                getTotalCards().size() != 0) {
            player1.getHand().add(player1.getCurrentDeck().getTotalCards().remove(0));
            player2.getHand().add(player2.getCurrentDeck().getTotalCards().remove(0));
        }
        int mana = 1, whoWon = 0;
        for (ActionsInput action : actions) {
            switch (action.getCommand()) {
                // active commands
                case "endPlayerTurn":
                    if (whoWon == 0) {
                        // before getting to the next turn, defrost frozen cards
                        players.get(howManyPlayersFinishedTheirTurn).defrost(table[players.
                                get(howManyPlayersFinishedTheirTurn).getIndexFrontRow()],
                                table[players.get(howManyPlayersFinishedTheirTurn).
                                        getIndexBackRow()]);
                        players.get(howManyPlayersFinishedTheirTurn).refreshAttackers(table[players.
                                get(howManyPlayersFinishedTheirTurn).getIndexFrontRow()],
                                table[players.get(howManyPlayersFinishedTheirTurn).
                                        getIndexBackRow()]);
                        players.get(howManyPlayersFinishedTheirTurn).getHero().
                                setHasAttacked(false);
                        ++howManyPlayersFinishedTheirTurn;

                        // end of a round, prepare new one
                        if (howManyPlayersFinishedTheirTurn == 2) {
                            howManyPlayersFinishedTheirTurn = 0;
                            if (mana < MAX_MANA) {
                                ++mana;
                            }
                            player1.setMana(player1.getMana() + mana);
                            player2.setMana(player2.getMana() + mana);

                            if (player1.getCurrentDeck().getTotalCards().size() != 0 && player2.
                                    getCurrentDeck().getTotalCards().size() != 0) {
                                player1.getHand().add(player1.getCurrentDeck().getTotalCards().
                                        remove(0));
                                player2.getHand().add(player2.getCurrentDeck().getTotalCards().
                                        remove(0));
                            }
                        }
                    }
                    break;
                case "placeCard":
                    if (whoWon == 0) {
                        Commands.RegularCommands.placeCard(action.getHandIdx(), players.
                                get(howManyPlayersFinishedTheirTurn), table, output);
                    }
                    break;
                case "cardUsesAttack":
                    if (whoWon == 0) {
                        Commands.RegularCommands.cardUsesAttack(action.getCardAttacker(),
                                action.getCardAttacked(),
                                players.get(howManyPlayersFinishedTheirTurn),
                                players.get((howManyPlayersFinishedTheirTurn + 1) % 2), table,
                                output);
                    }
                    break;
                case "useEnvironmentCard":
                    if (whoWon == 0) {
                        Commands.RegularCommands.useEnvironmentCard(action.getHandIdx(),
                                action.getAffectedRow(),
                                players.get(howManyPlayersFinishedTheirTurn),
                                players.get((howManyPlayersFinishedTheirTurn + 1) % 2), table,
                                output);
                    }
                    break;
                case "cardUsesAbility":
                    if (whoWon == 0) {
                        Commands.RegularCommands.cardUsesAbility(action.getCardAttacker(),
                                action.getCardAttacked(),
                                players.get(howManyPlayersFinishedTheirTurn),
                                players.get((howManyPlayersFinishedTheirTurn + 1) % 2), table,
                                output);
                    }
                    break;
                case "useAttackHero":
                    if (whoWon == 0) {
                        whoWon = Commands.RegularCommands.useAttackHero(action.getCardAttacker(),
                                players.get(howManyPlayersFinishedTheirTurn),
                                players.get((howManyPlayersFinishedTheirTurn + 1) % 2), table,
                                output);
                    }
                    if (whoWon == 1) {
                        Statistics.getInstance().setNumberWinsPlayer1(Statistics.getInstance().
                                getNumberWinsPlayer1() + 1);
                    }
                    if (whoWon == 2) {
                        Statistics.getInstance().setNumberWinsPlayer2(Statistics.getInstance().
                                getNumberWinsPlayer2() + 1);
                    }
                    Statistics.getInstance().setNumberOfGames(Statistics.getInstance().
                            getNumberWinsPlayer1() + Statistics.getInstance().
                            getNumberWinsPlayer2());
                    break;
                case "useHeroAbility":
                    if (whoWon == 0) {
                        Commands.RegularCommands.useHeroAbility(action.getAffectedRow(),
                                players.get(howManyPlayersFinishedTheirTurn),
                                players.get((howManyPlayersFinishedTheirTurn + 1) % 2), table,
                                output);
                    }
                    break;

                case "getPlayerDeck":
                    if (action.getPlayerIdx() == 1) {
                        Commands.DebugCommands.getPlayerDeck(action.getPlayerIdx(), player1,
                                output);
                    } else {
                        Commands.DebugCommands.getPlayerDeck(action.getPlayerIdx(), player2,
                                output);
                    }
                    break;
                case "getPlayerHero":
                    if (action.getPlayerIdx() == 1) {
                        Commands.DebugCommands.getPlayerHero(action.getPlayerIdx(), player1,
                                output);
                    } else {
                        Commands.DebugCommands.getPlayerHero(action.getPlayerIdx(), player2,
                                output);
                    }
                    break;
                case "getPlayerTurn":
                    Commands.DebugCommands.getPlayerTurn(players.
                            get(howManyPlayersFinishedTheirTurn), output);
                    break;
                case "getCardsInHand":
                    if (action.getPlayerIdx() == 1) {
                        Commands.DebugCommands.getCardsInHand(action.getPlayerIdx(), player1,
                                output);
                    } else {
                        Commands.DebugCommands.getCardsInHand(action.getPlayerIdx(), player2,
                                output);
                    }
                    break;
                case "getPlayerMana":
                    if (action.getPlayerIdx() == 1) {
                        Commands.DebugCommands.getPlayerMana(action.getPlayerIdx(), player1,
                                output);
                    } else {
                        Commands.DebugCommands.getPlayerMana(action.getPlayerIdx(), player2,
                                output);
                    }
                    break;
                case "getCardsOnTable":
                    Commands.DebugCommands.getCardsOnTable(table, output);
                    break;
                case "getEnvironmentCardsInHand":
                    if (action.getPlayerIdx() == 1) {
                        Commands.DebugCommands.getEnvironmentCardsInHand(action.getPlayerIdx(),
                                player1.getHand(), output);
                    } else {
                        Commands.DebugCommands.getEnvironmentCardsInHand(action.getPlayerIdx(),
                                player2.getHand(), output);
                    }
                    break;
                case "getCardAtPosition":
                    Commands.DebugCommands.getCardAtPosition(action.getX(), action.getY(), table,
                            output);
                    break;
                case "getFrozenCardsOnTable":
                    Commands.DebugCommands.getFrozenCardsOnTable(table, output);
                    break;
                case "getPlayerOneWins":
                    Commands.DebugCommands.getPlayerOneWins(Statistics.getInstance().
                            getNumberWinsPlayer1(), output);
                    break;
                case "getPlayerTwoWins":
                    Commands.DebugCommands.getPlayerTwoWins(Statistics.getInstance().
                            getNumberWinsPlayer2(), output);
                    break;
                case "getTotalGamesPlayed":
                    Commands.DebugCommands.getTotalGamesPlayed(Statistics.getInstance().
                            getNumberOfGames(), output);
                    break;
                default:
            }
        }
        return whoWon;
    }
}
