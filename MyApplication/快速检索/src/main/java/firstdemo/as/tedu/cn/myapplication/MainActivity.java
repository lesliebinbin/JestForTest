package firstdemo.as.tedu.cn.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.quickIndexBar)
    QuickIndexBar mQuickIndexBar;
    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.currentWord)
    TextView currentWord;

    private ArrayList<Friend> friends = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initQuickIndexBar();
        initListView();
    }

    private void initListView() {
        //1.准备数据
        fillList();
        //2.对数据进行排序
        Collections.sort(friends);
        //3.设置adapter
        mListView.setAdapter(new MyAdapter(this,friends));
    }

    private void fillList() {
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }

    private void initQuickIndexBar() {
        mQuickIndexBar.setOnLetterTouchListener(new QuickIndexBar.OnLetterTouchListener() {
            @Override
            public void onTouchLetter(String letter) {
                //根据当前触摸的字母,去集合中找每个item的首字母,然后将对应的item
                //放到屏幕顶端
                for(int i=0;i<friends.size();i++){
                    String firstWord = friends.get(i).getPinyin().substring(0,1);
                    if(letter.equals(firstWord)){
                        mListView.setSelection(i);
                        break;
                    }
                }
                //显示当前触摸的字母
                showCurrentWord(letter);
            }
        });

        //通过缩小currentWord来隐藏
        currentWord.setScaleX(0);
        currentWord.setScaleX(0);

    }
    private boolean isScale = false;
    private Handler handler = new Handler();

    private void showCurrentWord(String letter) {

        currentWord.setText(letter);
        if(!isScale){
            isScale = true;
            ObjectAnimator objX =  ObjectAnimator.ofFloat(currentWord,"scaleX",0,1f);
            ObjectAnimator objY =  ObjectAnimator.ofFloat(currentWord,"scaleY",0,1f);
            objX.setInterpolator(new OvershootInterpolator());
            objY.setInterpolator(new OvershootInterpolator());
            objX.setDuration(500);
            objY.setDuration(500);
            objX.start();
            objY.start();
        }


        //每次调用之前先移除之前的任务
        handler.removeCallbacksAndMessages(null);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objX =  ObjectAnimator.ofFloat(currentWord,"scaleX",1f,0);
                ObjectAnimator objY =  ObjectAnimator.ofFloat(currentWord,"scaleY",1f,0);
                objX.setInterpolator(new OvershootInterpolator());
                objY.setInterpolator(new OvershootInterpolator());
                objX.setDuration(500);
                objY.setDuration(500);
                objX.start();
                objY.start();
                isScale = false;
            }
        },1500);
    }
}
