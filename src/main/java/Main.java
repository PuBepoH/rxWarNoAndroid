import model.ModelFacade;
import model.TestModel;
import model.TestModelRemake;
import presenter.PresenterFacade;
import presenter.TestPresenter;
import view.MenuAction;
import view.NewAction;
import view.TestView;
import view.ViewFacade;

public class Main {
    public static void main(String[] args) {
        TestView v = new TestView();
        TestPresenter p = new TestPresenter();
        TestModelRemake m = new TestModelRemake();

        v.initialize(p);
        p.initialize(v,m,null);

        //p.getFragmentControlState().subscribe(System.out::println);


        v.getMenuActions().filter(s -> s== MenuAction.EXIT).take(1).blockingSubscribe();
    }


}
