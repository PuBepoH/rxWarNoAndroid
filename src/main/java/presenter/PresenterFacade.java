package presenter;

import data.DataFacade;
import io.reactivex.subjects.BehaviorSubject;
import model.ModelFacade;
import view.ViewFacade;

public interface PresenterFacade {
    public void initialize(ViewFacade viewFacade, ModelFacade modelFacade, DataFacade dataFacade);
    public BehaviorSubject<String> getFragmentControlState();
    public BehaviorSubject<String> getMenuState();
    public BehaviorSubject<FieldState> getFieldState();
    public BehaviorSubject<Integer> getTimerState();
    public BehaviorSubject<TurnsState> getTurnsState();
    public NewState getNewGameState();
}
