package model;

import com.sun.media.jfxmediaimpl.MediaDisposer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import presenter.FieldState;
import presenter.TurnsState;
import view.GameActionType;

public class TestModel implements ModelFacade {
    PublishSubject<ModelCommand> command= PublishSubject.create();
    BehaviorSubject<Integer> timerState= BehaviorSubject.create();
    BehaviorSubject<FieldState> fieldState = BehaviorSubject.create();
    BehaviorSubject<TurnsState> turnState = BehaviorSubject.create();


    CompositeDisposable commandSubscription = new CompositeDisposable();
    CompositeDisposable internalSubscriptions = new CompositeDisposable();

    public TestModel(){
        //SET
        internalSubscriptions.add(
            command
                .filter(o->o.type==ModelCommandType.SET)
                .subscribe(o->fieldState.onNext(o.fieldState))
        );
        //NEW

        //TURN

        //PAUSE

        //RESUME
    }

    @Override
    public void setCommand(PublishSubject<ModelCommand> command) {
        commandSubscription.dispose();
        commandSubscription = new CompositeDisposable();
        commandSubscription.add(command.subscribe(this.command::onNext));
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
    public BehaviorSubject<TurnsState> getTurnsState() {
        return turnState;
    }
}
