import model.ModelFacade;
import model.TestModel;
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
        TestModel m = new TestModel();

        v.initialize(p);
        p.initialize(v,m,null);


        //v.getMenuActions().subscribe(o->System.out.println("m: "+o));
        //v.getNewActions().subscribe(o->System.out.println("n: "+o.getType()+" "+o.getPlayers()+" "+o.getColors()));
        //v.getGameActions().subscribe(o->System.out.println("g: "+o.getType()+" "+o.getPlayer()+" "+o.getColor()));

        //m.getFieldState().subscribe(o->System.out.println("fs"));
        //m.getPlayerPanelState().subscribe(o->System.out.println("pps"));

        //System.out.println("GO");
        v.getMenuActions().filter(s -> s== MenuAction.EXIT).take(1).blockingSubscribe();
        //System.out.println("END");
    }


}
