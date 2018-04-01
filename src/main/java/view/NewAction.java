package view;

public class NewAction {
    private NewActionType type;
    private int players;
    private int colors;

    public NewAction(int players, int colors, NewActionType type){
        this.players=players;
        this.colors=colors;
        this.type=type;
    }

    public int getPlayers() {
        return players;
    }

    public int getColors() {
        return colors;
    }

    public NewActionType isNewGame() {
        return type;
    }
}
