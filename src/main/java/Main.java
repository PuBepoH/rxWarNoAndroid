import view.TestView;

public class Main {
    public static void main(String[] args) {
        TestView tv = new TestView();
        tv.getMenuActions().subscribe(System.out::println);
        System.out.println("GO");
        tv.getMenuActions().filter(s -> s.equals("exit")).take(1).blockingSubscribe();
        System.out.println("END");
    }

}
