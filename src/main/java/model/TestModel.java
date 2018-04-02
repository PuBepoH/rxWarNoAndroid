package model;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import presenter.FieldState;
import presenter.PlayerPanelState;

public class TestModel implements ModelFacade{
    PublishSubject<ModelCommand> command= PublishSubject.create();
    BehaviorSubject<Integer> timerState= BehaviorSubject.create();
    BehaviorSubject<FieldState> fieldState = BehaviorSubject.create();
    BehaviorSubject<PlayerPanelState> playerPanelState = BehaviorSubject.create();


    CompositeDisposable commandSubscription = new CompositeDisposable();
    CompositeDisposable internalSubscriptions = new CompositeDisposable();

    public TestModel(){
        //SET

        //NEW

        //TURN

        //PAUSE RESUME
    }

    @Override
    public void setCommand(PublishSubject<ModelCommand> command) {
        commandSubscription.dispose();
        commandSubscription = new CompositeDisposable();
        commandSubscription.add(
            command.subscribe(this.command::onNext)
        );
    }

    @Override
    public BehaviorSubject<Integer> getTimerState() {
        return timerState;
    }

    @Override
    public BehaviorSubject<FieldState> getFieldState() {
        return fieldState;
    }

    @Override
    public BehaviorSubject<PlayerPanelState> getPlayerPanelState() {
        return playerPanelState;
    }
}
