package model;

public class WinEvent {
    private int numberOfPlayers;
    private int[] score;

    public WinEvent(int numberOfPlayers, int[] score) {
        this.numberOfPlayers = numberOfPlayers;
        this.score = score;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int[] getScore() {
        return score;
    }
}
