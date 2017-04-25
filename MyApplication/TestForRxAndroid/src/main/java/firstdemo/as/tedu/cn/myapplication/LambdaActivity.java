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

public class LambdaActivity extends AppCompatActivity {
    @Bind(R.id.simple_tv_text)
    TextView mTextView;
    final String [] myManyWords = {"Hello","I","am","your","friend","Spike"};
    final List<String> myManyWordsList = Arrays.asList(myManyWords);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambda);
        ButterKnife.bind(this);
        //添加字符串,省略Consumer的其他方法,只使用一个onNext
        Observable<String> onShow = Observable.just(sayMyName());
        //先映射,再设置TextView
        onShow.map(String::toUpperCase).subscribe(mTextView::setText);
        //单独显示数组中的每一个元素
        Observable<String> obMap = Observable.fromArray(myManyWords);
        obMap.observeOn(AndroidSchedulers.mainThread())
                .map(String::toUpperCase).subscribe(this::showToast);

        //优化过的代码
        Observable.just(myManyWordsList)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(Observable::fromIterable)
                .reduce(this::mergeString)
                .subscribe(this::showToast);
    }

    // 创建字符串
    private String sayMyName() {
        return "Hello, I am your friend, Spike!";
    }

    // 显示Toast
    private void showToast(String s) {
        Toast.makeText(LambdaActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    // 合并字符串
    private String mergeString(String s1, String s2) {
        return String.format("%s %s", s1, s2);
    }

}
