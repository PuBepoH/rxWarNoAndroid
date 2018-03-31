import view.NewAction;
import view.TestView;

public class Main {
    public static void main(String[] args) {
        TestView tv = new TestView();
        tv.getMenuActions().subscribe(o->System.out.println("m: "+o));
        tv.getNewActions().subscribe(o->System.out.println("n: "+o.isNewGame()+" "+o.getPlayers()+" "+o.getColors()));
        tv.getGameActions().subscribe(o->System.out.println("g: "+o.getType()+" "+o.getPlayer()+" "+o.getColor()));
        System.out.println("GO");
        tv.getMenuActions().filter(s -> s.equals("exit")).take(1).blockingSubscribe();
        System.out.println("END");
    }

}
