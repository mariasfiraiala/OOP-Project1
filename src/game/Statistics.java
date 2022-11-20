package game;

public class Statistics {
    private int numberWinsPlayer1 = 0,  numberWinsPlayer2 = 0;
    private int numberOfGames = 0;

    private static Statistics instance = null;
    private Statistics(){}

    public static Statistics getInstance() {
        if (instance == null) {
            instance = new Statistics();
        }
        return instance;
    }

    public int getNumberWinsPlayer1() {
        return numberWinsPlayer1;
    }

    public int getNumberWinsPlayer2() {
        return numberWinsPlayer2;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberWinsPlayer1(int numberWinsPlayer1) {
        this.numberWinsPlayer1 = numberWinsPlayer1;
    }

    public void setNumberWinsPlayer2(int numberWinsPlayer2) {
        this.numberWinsPlayer2 = numberWinsPlayer2;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void reset() {
        numberOfGames = numberWinsPlayer1 = numberWinsPlayer2 = 0;
    }
}
