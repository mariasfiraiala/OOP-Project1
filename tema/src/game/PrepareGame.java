package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;

public class PrepareGame {
    private int numberWinsPlayer1;
    private int numberWinsPlayer2;

    public PrepareGame(Input input, ArrayNode output) {
        numberWinsPlayer1 = numberWinsPlayer2 = 0;

        for (GameInput game : input.getGames()) {
            Game newGame = new Game(game, input.getPlayerOneDecks(), input.getPlayerTwoDecks());
            newGame.shuffle();
            if (newGame.letThePlayersPlay(input.getGames().get(input.getGames().indexOf(game)).getActions(), output) == 1)
                ++numberWinsPlayer1;
            else
                ++numberWinsPlayer2;
        }
    }
}
