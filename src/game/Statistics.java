package game;

public final class Statistics {
    private int numberWinsPlayer1 = 0,  numberWinsPlayer2 = 0;
    private int numberOfGames = 0;

    private static Statistics instance = null;
    private Statistics() { }

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

    public void setNumberWinsPlayer1(final int numberWinsPlayer1) {
        this.numberWinsPlayer1 = numberWinsPlayer1;
    }

    public void setNumberWinsPlayer2(final int numberWinsPlayer2) {
        this.numberWinsPlayer2 = numberWinsPlayer2;
    }

    public void setNumberOfGames(final int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public void reset() {
        numberOfGames = 0;
        numberWinsPlayer1 = 0;
        numberWinsPlayer2 = 0;
    }
}
