package view;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;
import presenter.PresenterFacade;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestView implements ViewFacade{
    private CompositeDisposable externalDiposables = new CompositeDisposable();
    private CompositeDisposable internalDisposables = new CompositeDisposable();
    private PublishSubject<String> menuActions;
    private PublishSubject<MainAction> mainAction;

    private Observable<String> inputFlow;

    Boolean exit=false;

    public void TestView(){

        Observable<String> inputFlow = Observable.create(s->{

            s.onNext("dsfgdfg");
            s.onComplete();
        });
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
    public PublishSubject<MainAction> getMainActions() {
        return mainAction;
    }

    @Override
    public PublishSubject<NewAction> getNewActions() {
        return null;
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
