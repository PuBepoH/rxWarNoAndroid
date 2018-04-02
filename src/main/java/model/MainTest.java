package model;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.util.Pair;

public class MainTest {
    public static void main(String[] args){
        PublishSubject<Integer> ps1= PublishSubject.create();

        ps1.distinct().subscribe(o->{
            if (o+1<10){
                ps1.onNext(o+1);
            }
            if (o-1>0){
                ps1.onNext(o-1);
            }
        });

        Observable<Integer> ooo = Observable.just(5);

        ooo.flatMap(o->ps1).subscribe(System.out::println);

        //ps1.distinct().subscribe(System.out::println);
        //ps1.onNext(5);




    }


}
