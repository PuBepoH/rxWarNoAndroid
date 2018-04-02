package model;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import presenter.FieldState;
import presenter.PlayerPanelState;

public interface ModelFacade {
    public void setCommand(PublishSubject<ModelCommand> command);
    public BehaviorSubject<Integer> getTimerState();
    public BehaviorSubject<FieldState> getFieldState();
    public BehaviorSubject<PlayerPanelState> getPlayerPanelState();
    public PublishSubject<WinEvent> getWinEvent();
}
