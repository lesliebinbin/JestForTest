package firstdemo.as.tedu.cn.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MoreActivity extends AppCompatActivity {

    @Bind(R.id.simple_tv_text)TextView mTextView;
    final String[] myManyWords = {"Hello","I","am","your","friend","Spike"};
    final List<String> myManyWordsList = Arrays.asList(myManyWords);


    //创将Consumer消费者,替代旧版本的Action
    private Consumer<String> mTextViewConsumer =
            new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    mTextView.setText(s);
                }
            };

    private Consumer<String> mToastConcumser = new Consumer<String>() {
        @Override
        public void accept(String s) throws Exception {
            Toast.makeText(MoreActivity.this,s, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        ButterKnife.bind(this);


        // 添加字符串, 省略的其他方法, 只使用一个onNext.

        Observable<String> obShow = Observable.just(sayMyName());

        //先映射,再设置TextView
        obShow.observeOn(AndroidSchedulers.mainThread())
                .map(mUpperLetterFunc).subscribe(mTextViewConsumer);
        //单独显示数组中的每一个元素
        Observable<String> obMap = Observable.fromArray(myManyWords);
        obMap.observeOn(AndroidSchedulers.mainThread())
                .map(mUpperLetterFunc).subscribe(mToastConcumser);
        //优化过的代码直接获取数组,再分发,再合并
        Observable.just(myManyWordsList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(mOneLetter)
                .reduce(mMergeStringFunc)
                .subscribe(mToastConcumser);
    }

    private Function<String,String> mUpperLetterFunc = new Function<String, String>() {
        @Override
        public String apply(String s) throws Exception {
            return s.toUpperCase();
        }
    };

    private Function<List<String>,Observable<String>> mOneLetter =
            new Function<List<String>, Observable<String>>() {
                @Override
                public Observable<String> apply(List<String> strings) throws Exception {
                    return Observable.fromIterable(strings);
                }
            };


    private BiFunction<String,String,String> mMergeStringFunc
            = new BiFunction<String, String, String>() {
        @Override
        public String apply(String s, String s2) throws Exception {
            return String.format("%s %s",s,s2);
        }
    };




    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend, Spike!";
    }

}
