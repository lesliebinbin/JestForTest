package firstdemo.as.tedu.cn.androiduidemo2;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by pc on 2017/3/22.
 */

public class ExampleUnitTest2 {

    @Test
    public void rxTest1(){
        Observable.just("hello world").map(new Func1<String,String>() {

            @Override
            public String call(String s) {
                return s+"Leslie";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String ss) {
                System.out.println(ss);
            }
        });
    }

    @Test
    public void test2(){
        Observable.just("Hello World").map(s-> s+"\nLeslie")
                .subscribe(s->System.out.println(s));
    }

    @Test
    public void test3(){
        Observable.just("HiHi").map((s)->Integer.valueOf(s.hashCode()))
                .subscribe(s->System.out.println(s));
    }

    @Test
    public void test4(){
        Observable.from(new String[]{"hello","hi","how are you"})
                .subscribe(s->System.out.println(s));
    }
}
