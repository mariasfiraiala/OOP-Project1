package game;

public class Statistics {
    private static int numberWinsPlayer1 = 0,  numberWinsPlayer2 = 0;
    private static int numberOfGames = 0;

    public static int getNumberWinsPlayer1() {
        return numberWinsPlayer1;
    }

    public static int getNumberWinsPlayer2() {
        return numberWinsPlayer2;
    }

    public static int getNumberOfGames() {
        return numberOfGames;
    }

    public static void setNumberWinsPlayer1(int numberWinsPlayer1) {
        Statistics.numberWinsPlayer1 = numberWinsPlayer1;
    }

    public static void setNumberWinsPlayer2(int numberWinsPlayer2) {
        Statistics.numberWinsPlayer2 = numberWinsPlayer2;
    }

    public static void setNumberOfGames(int numberOfGames) {
        Statistics.numberOfGames = numberOfGames;
    }
}
