package view;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import presenter.PresenterFacade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TestView implements ViewFacade{
    private CompositeDisposable externalDiposables = new CompositeDisposable();
    private CompositeDisposable internalDisposables = new CompositeDisposable();

    private PublishSubject<String> menuActions = PublishSubject.create();
    private PublishSubject<GameAction> gameActions = PublishSubject.create();
    private PublishSubject<NewAction> newActions = PublishSubject.create();

    private ConnectableObservable<String[]> inputFlow;

    private boolean exit=false;

    public TestView(){
        Observable<String> rawFlow = Observable.create(s->{
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            while (!s.isDisposed()) {
                s.onNext(b.readLine());
            }
            s.onComplete();
        });
        inputFlow = rawFlow
                .map(s -> s.split(" "))
                .subscribeOn(Schedulers.io())
                .publish();
        //menu
        internalDisposables.add(inputFlow
                .filter(s->s[0].equals("m"))
                .filter(s-> {
                    switch (s[1]) {
                        case "resume":
                        case "new game":
                        case "exit":
                            return true;
                        default:
                            return false;
                    }
                })
                .subscribe(s->menuActions.onNext(s[1])));
        //newAction
        internalDisposables.add(inputFlow
                .filter(s->s[0].equals("n"))
                .filter(s->(s[1].equals("+"))|(s[1].equals("-")))
                .map(s -> new NewAction(Integer.valueOf(s[2]),Integer.valueOf(s[3]),s[1].equals("+")))
                .subscribe(newActions::onNext));
        //gameActions
        internalDisposables.add(inputFlow
                .filter(s->s[0].equals("g"))

        internalDisposables.add(inputFlow.connect());
    }

    @Override
    public void initialize(PresenterFacade presenterFacade) {
        externalDiposables.dispose();
        externalDiposables.clear();
        externalDiposables.add(presenterFacade.getFragmentControlState().subscribe(System.out::println));
    }

    @Override
    public PublishSubject<String> getMenuActions() {
        return menuActions;
    }

    @Override
    public PublishSubject<GameAction> getGameActions() {
        return gameActions;
    }

    @Override
    public PublishSubject<NewAction> getNewActions() {
        return newActions;
    }

    @Override
    protected void finalize() throws Throwable {
        externalDiposables.dispose();
        externalDiposables.clear();
        internalDisposables.dispose();
        internalDisposables.clear();
        super.finalize();
    }

}
