package presenter;

public class TurnsState {
    private int numberOfColors;
    private int numberOfPlayers;
    private boolean[][] blocked;

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public void setNumberOfColors(int numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setBlocked(boolean[][] blocked) {
        this.blocked = blocked;
    }

    public boolean getBlocked(int player, int color) {
        return blocked[player][color];
    }
}
