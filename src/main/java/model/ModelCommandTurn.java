package model;

public class ModelCommandTurn extends ModelCommand {
    public int player;
    public int color;

    public ModelCommandTurn(int player, int color) {
        this.player = player;
        this.color = color;
    }

    public int getPlayer() {
        return player;
    }

    public int getColor() {
        return color;
    }
}
