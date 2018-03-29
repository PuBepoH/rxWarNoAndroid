package presenter;

import data.DataFacade;
import io.reactivex.subjects.BehaviorSubject;
import model.ModelFacade;
import view.ViewFacade;

public class TestPresenter implements PresenterFacade {
    @Override
    public void initialize(ViewFacade viewFacade, ModelFacade modelFacade, DataFacade dataFacade) {

    }

    @Override
    public BehaviorSubject<String> getFragmentControlState() {
        return null;
    }

    @Override
    public BehaviorSubject<String> getMenuState() {
        return null;
    }

    @Override
    public BehaviorSubject<FieldState> getFieldState() {
        return null;
    }

    @Override
    public BehaviorSubject<Integer> getTimerState() {
        return null;
    }

    @Override
    public BehaviorSubject<TurnsState> getTurnsState() {
        return null;
    }

    @Override
    public NewState getNewGameState() {
        return null;
    }
}
