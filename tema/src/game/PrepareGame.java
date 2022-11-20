package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;

public class PrepareGame {
    private int numberWinsPlayer1;
    private int numberWinsPlayer2;

    public PrepareGame(Input input, ArrayNode output) {
        Statistics.setNumberOfGames(input.getGames().size());

        for (GameInput game : input.getGames()) {
            Game newGame = new Game(game, input.getPlayerOneDecks(), input.getPlayerTwoDecks());
            newGame.shuffle();
            int whoWon = newGame.letThePlayersPlay(input.getGames().get(input.getGames().indexOf(game)).getActions(), output);

            if (whoWon == 1) {
                Statistics.setNumberWinsPlayer1(Statistics.getNumberWinsPlayer1() + 1);
            } if (whoWon == 2){
                Statistics.setNumberWinsPlayer2(Statistics.getNumberWinsPlayer2() + 1);
            }
        }
    }
}
