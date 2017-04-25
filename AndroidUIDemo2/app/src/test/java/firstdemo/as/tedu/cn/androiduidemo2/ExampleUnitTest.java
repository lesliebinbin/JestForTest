package firstdemo.as.tedu.cn.androiduidemo2;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello World");
            subscriber.onCompleted();
        }
    });


    Subscriber<String> mSubscribers = new Subscriber<String>(){

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            System.out.println(s);
        }
    };

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testTheRx(){
        myObservable.subscribe(mSubscribers);
    }

    Observable<String> mObservable = Observable.just("Hello Fuck");
    Subscriber<String> mStringSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            System.out.println(s);
        }
    };

    @Test
    public void testTheRx2(){
        mObservable.subscribe(mStringSubscriber);
    }
}