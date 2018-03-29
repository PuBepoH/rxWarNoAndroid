import io.reactivex.Observable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        boolean exit=false;
        Observable<String> q= null;
        try {
            q = Observable.just(
                (new BufferedReader(new InputStreamReader(System.in))).readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Observable.
        q=q.concatWith(q);
        q=q.concatWith(q);
        q.subscribe(System.out::println);
    }

}
