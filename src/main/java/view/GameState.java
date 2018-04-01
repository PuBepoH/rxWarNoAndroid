package view;

import presenter.FieldState;
import presenter.TurnsState;

public class GameState {
    private FieldState fieldState;
    private TurnsState turnsState;
    private int timerState;

    public GameState(FieldState fieldState, TurnsState turnsState, int timerState) {
        this.fieldState = fieldState;
        this.turnsState = turnsState;
        this.timerState = timerState;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public TurnsState getTurnsState() {
        return turnsState;
    }

    public int getTimerState() {
        return timerState;
    }
}
