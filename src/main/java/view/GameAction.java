package view;

public class GameAction {
    private String type;
    private int player;
    private int color;

    public GameAction(String type, int player, int color){
        this.type=type;
        this.player=player;
        this.color=color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
