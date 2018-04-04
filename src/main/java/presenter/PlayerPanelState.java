package presenter;

public class PlayerPanelState {
    private int numberOfColors;
    private boolean twoPlayers;
    private boolean[][] blocked;
    private int turnOfPlayer;

    public PlayerPanelState(int numberOfColors, boolean twoPlayers, boolean[][] blocked, int turnOfPlayer) {
        this.numberOfColors = numberOfColors;
        this.twoPlayers = twoPlayers;
        this.turnOfPlayer = turnOfPlayer;
        this.blocked=blocked.clone();
        for (int i=0;i<blocked.length;i++){
            this.blocked[i]=blocked[i].clone();
        }
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public boolean isTwoPlayers() {
        return twoPlayers;
    }

    public boolean isBlocked(int x,int y) {
        return blocked[x][y];
    }

    public int getTurnOfPlayer() {
        return turnOfPlayer;
    }
}
