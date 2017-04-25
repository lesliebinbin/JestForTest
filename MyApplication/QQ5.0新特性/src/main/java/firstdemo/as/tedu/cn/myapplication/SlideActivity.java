package firstdemo.as.tedu.cn.myapplication;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.animation.ObjectAnimator.ofFloat;

public class SlideActivity extends AppCompatActivity {

    @Bind(R.id.menu_listview)
    ListView mMenuListview;
    @Bind(R.id.iv_head)
    ImageView mIvHead;
    @Bind(R.id.main_listview)
    ListView mMainListview;
    @Bind(R.id.my_layout)
    MyLinearLayout mMyLayout;
    @Bind(R.id.sl_slide_menu)
    SlideMenu mSlideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        ButterKnife.bind(this);
        initSlideMenu();
        initListView();
    }

    private void initSlideMenu() {
        mSlideMenu.setOnDragStateChangedListener(new SlideMenu.OnDragStateChangedListener() {
            @Override
            public void onOpen() {
                mMenuListview.smoothScrollToPosition(
                        new Random().nextInt(mMenuListview.getCount()));
            }

           @Override
            public void onClose() {
               ObjectAnimator animator = ObjectAnimator.ofFloat(mIvHead, "translationX", 0, 15).setDuration(500);
               animator.setInterpolator(new CycleInterpolator(8));
               animator.start();

           }
            @Override
            public void onDraging(float fraction) {
                mIvHead.setAlpha(1-fraction);
            }
        });
        mMyLayout.setSlideMenu(mSlideMenu);
    }

    private void initListView() {
        mMenuListview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Constant.sCheeseStrings){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });

        mMainListview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Constant.NAMES){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = convertView==null?super.getView(position,convertView,parent):convertView;
                //先缩小view
                view.setScaleX(0.5f);
                view.setScaleY(0.5f);
                //以属性动画放大
                ofFloat(view,"scaleX",0.5f,1f)
                .setDuration(350).start();
                ofFloat(view,"scaleY",0.5f,1f)
                        .setDuration(350).start();
                return  view;
            }
        });
    }
}
