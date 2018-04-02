package view;

import presenter.FieldState;
import presenter.PlayerPanelState;

public class GameState {
    private FieldState fieldState;
    private PlayerPanelState playerPanelState;
    private int timerState;

    public GameState(FieldState fieldState, PlayerPanelState playerPanelState, int timerState) {
        this.fieldState = fieldState;
        this.playerPanelState = playerPanelState;
        this.timerState = timerState;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public PlayerPanelState getPlayerPanelState() {
        return playerPanelState;
    }

    public int getTimerState() {
        return timerState;
    }
}
