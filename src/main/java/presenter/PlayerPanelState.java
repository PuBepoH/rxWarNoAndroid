package presenter;

public class PlayerPanelState {
    private int numberOfColors;
    private int numberOfPlayers;
    private boolean[][] blocked;
    private int turnOfPlayer;

    public PlayerPanelState(int numberOfColors, int numberOfPlayers, boolean[][] blocked, int turnOfPlayer) {
        this.numberOfColors = numberOfColors;
        this.numberOfPlayers = numberOfPlayers;
        this.blocked = blocked;
        this.turnOfPlayer = turnOfPlayer;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean getBlocked(int player, int color) {
        return blocked[player][color];
    }

    public int getTurnOfPlayer() {
        return turnOfPlayer;
    }
}
