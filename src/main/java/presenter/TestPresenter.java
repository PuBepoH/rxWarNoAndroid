package presenter;

import data.DataFacade;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import model.ModelFacade;
import view.TestView;
import view.ViewFacade;

import java.util.concurrent.TimeUnit;

public class TestPresenter implements PresenterFacade {
    private BehaviorSubject<FragmentName> fragmentControlState = BehaviorSubject.createDefault(FragmentName.MENU);
    private BehaviorSubject<MenuState> menuState = BehaviorSubject.createDefault(MenuState.UNRESUMABLE);
    private BehaviorSubject<FieldState> fieldState = BehaviorSubject.create();
    private BehaviorSubject<Integer> timerState = BehaviorSubject.create();
    private BehaviorSubject<TurnsState> turnsState = BehaviorSubject.create();

    private CompositeDisposable internalDisposables = new CompositeDisposable();
    private CompositeDisposable externalDisposables = new CompositeDisposable();

    public TestPresenter(){

        Observable.interval(3, TimeUnit.SECONDS)
                .map(l->{
                    switch ((int)(l%3)) {
                        case 0:
                            return FragmentName.MENU;
                        case 1:
                            return FragmentName.NEW;
                        case 2:
                            return FragmentName.GAME;
                        default:
                            return null;
                    }
                })
                .subscribe(fragmentControlState);


    }

    @Override
    public void initialize(ViewFacade viewFacade, ModelFacade modelFacade, DataFacade dataFacade) {
        externalDisposables.dispose();
        externalDisposables = new CompositeDisposable();

    }

    @Override
    public BehaviorSubject<FragmentName> getFragmentControlState() {
        return fragmentControlState;
    }

    @Override
    public BehaviorSubject<MenuState> getMenuState() {
        return menuState;
    }

    @Override
    public BehaviorSubject<FieldState> getFieldState() {
        return fieldState;
    }

    @Override
    public BehaviorSubject<Integer> getTimerState() {
        return timerState;
    }

    @Override
    public BehaviorSubject<TurnsState> getTurnsState() {
        return turnsState;
    }

    @Override
    public NewState getNewGameState() {
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        internalDisposables.dispose();
        internalDisposables.clear();
        externalDisposables.dispose();
        externalDisposables.clear();
        super.finalize();
    }
}
