package firstdemo.as.tedu.cn.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SimpleActivity extends AppCompatActivity {

    @Bind(R.id.simple_tv_text)
    TextView mTextView;

    ObservableOnSubscribe<String> mObservableAction = new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter e) throws Exception {
            e.onNext(sayMyName());
            e.onComplete();
        }
    };

    private Observer<String> mTextSubscriber = new Observer<String>() {


        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String value) {
            mTextView.setText(value);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    private Observer<String> mToastSubscriber = new Observer<String>() {


        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String value) {
            Toast.makeText(SimpleActivity.this,sayMyName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        ButterKnife.bind(this);


        // 注册观察活动
        @SuppressWarnings("unchecked")
        Observable<String> observable = Observable.create(mObservableAction);

        // 分发订阅信息
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe( mToastSubscriber);
        observable.subscribe(mTextSubscriber);
    }

    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend, Spike!";
    }


}
