package view;


public class NewAction {
    private boolean newGame;
    private int players;
    private int colors;

    public NewAction(int players, int colors, boolean newGame){
        this.players=players;
        this.colors=colors;
        this.newGame=newGame;
    }

    public int getPlayers() {
        return players;
    }

    public int getColors() {
        return colors;
    }

    public boolean isNewGame() {
        return newGame;
    }
}
