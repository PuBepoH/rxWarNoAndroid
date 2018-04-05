package model;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import presenter.FieldState;
import presenter.PlayerPanelState;

public interface ModelFacade {
    void setCommand(PublishSubject<ModelCommand> command);
    Observable<Integer> getTimerState();
    Observable<FieldState> getFieldState();
    Observable<PlayerPanelState> getPlayerPanelState();
    Observable<WinEvent> getWinEvent();
}
