package model;

public class WinEvent {
    private boolean twoPlayers;
    private int[] score;

    public WinEvent(boolean twoPlayers, int[] score, int maxScore) {
        this.twoPlayers = twoPlayers;
        this.score = score.clone();
        this.maxScore=maxScore;
    }

    public boolean isTwoPlayers() {
        return twoPlayers;
    }

    public int getScore(int i) {
        return score[i];
    }

    public int getMaxScore() {
        return maxScore;
    }
}
