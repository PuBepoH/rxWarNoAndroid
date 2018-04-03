package model;

public class WinEvent {
    private boolean twoPlayers;
    private int[] score;

    public WinEvent(boolean twoPlayers, int[] score) {
        this.twoPlayers = twoPlayers;
        this.score = score;
    }

    public boolean isTwoPlayers() {
        return twoPlayers;
    }

    public int getScore(int i) {
        return score[i];
    }
}
