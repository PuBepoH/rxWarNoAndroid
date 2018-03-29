package view;

import io.reactivex.subjects.PublishSubject;
import presenter.PresenterFacade;

public class TestView implements ViewFacade{
    

    @Override
    public void initialize(PresenterFacade presenterFacade) {

    }

    @Override
    public PublishSubject<String> getMenuActions() {
        return null;
    }

    @Override
    public PublishSubject<MainAction> getMainActions() {
        return null;
    }

    @Override
    public PublishSubject<NewAction> getNewActions() {
        return null;
    }
}
