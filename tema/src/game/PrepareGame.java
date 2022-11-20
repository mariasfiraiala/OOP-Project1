package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;

public class PrepareGame {
    public PrepareGame(Input input, ArrayNode output) {
        Statistics.getInstance().reset();
        for (GameInput game : input.getGames()) {
            Game newGame = new Game(game, input.getPlayerOneDecks(), input.getPlayerTwoDecks());
            newGame.shuffle();
            newGame.letThePlayersPlay(input.getGames().get(input.getGames().indexOf(game)).getActions(), output);
        }
    }
}
