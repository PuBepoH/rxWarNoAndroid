package view;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;
import presenter.PresenterFacade;

public interface ViewFacade {
    public Observable<MenuAction> getMenuActions();
    public Observable<GameAction> getGameActions();
    public Observable<NewAction> getNewActions();
    public Observable<Integer> getWinActions();
}
