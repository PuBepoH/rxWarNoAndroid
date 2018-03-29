package view;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;
import presenter.PresenterFacade;

public interface ViewFacade {
    public void initialize(PresenterFacade presenterFacade);
    public PublishSubject<String> getMenuActions();
    public PublishSubject<GameAction> getGameActions();
    public PublishSubject<NewAction> getNewActions();
}
