package model;

public class ModelCommandSet extends ModelCommand{
    public int[] fieldState;
    public int fieldX;
    public int fieldY;
    public int timerState;
    public int turnOfPlayer;
    public int numberOfColors;

    public ModelCommandSet(int[] fieldState, int fieldX, int fieldY, int timerState, int turnOfPlayer, int numberOfColors) {
        this.fieldState = fieldState;
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.timerState = timerState;
        this.turnOfPlayer = turnOfPlayer;
        this.numberOfColors = numberOfColors;
    }

    public int[] getFieldState() {
        return fieldState;
    }

    public int getFieldX() {
        return fieldX;
    }

    public int getFieldY() {
        return fieldY;
    }

    public int getTimerState() {
        return timerState;
    }

    public int getTurnOfPlayer() {
        return turnOfPlayer;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }
}
