package model;

public class ModelCommandNew extends ModelCommand {
    private int numberOfPlayers;
    private int numberOfColors;

    public ModelCommandNew(int numberOfPlayers, int numberOfColors) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfColors = numberOfColors;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }
}
