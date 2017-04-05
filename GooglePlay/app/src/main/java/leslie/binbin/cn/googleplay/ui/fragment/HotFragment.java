package leslie.binbin.cn.googleplay.ui.fragment;


import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import leslie.binbin.cn.googleplay.http.protocol.HotProtocol;
import leslie.binbin.cn.googleplay.ui.view.FlowLayout;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.utils.DrawableUtils;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class HotFragment extends BaseFragment {

    private ArrayList<String> mData;

    @Override
    public View onCreateSuccessView() {

        //支持上下滑
        ScrollView scrollView = new ScrollView(UIUtils.getContext());

        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());

        int padding = UIUtils.dip2px(10);

        scrollView.addView(flowLayout);
        flowLayout.setPadding(padding,padding,padding,padding);//设置内边距

        flowLayout.setHorizontalSpacing(UIUtils.dip2px(6));//水平间距
        flowLayout.setVerticalSpacing(UIUtils.dip2px(8));//垂直间距

        for(int i = 0;i<mData.size();i++){
            TextView view = new TextView(UIUtils.getContext());
            String keyword = mData.get(i);
            view.setText(keyword);

            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            view.setPadding(padding,padding,padding,padding);
            view.setGravity(Gravity.CENTER);

            Random random = new Random();
            int r = 30+random.nextInt(200);
            int g = 30+random.nextInt(200);
            int b = 30+random.nextInt(200);

            int color = 0xffcecece;//按下后的背景色


            StateListDrawable selector = DrawableUtils.getSelector(Color.rgb(r, g, b), color, UIUtils.dip2px(6));

            view.setBackgroundDrawable(selector);

            //只有设置点击事件,选择器才起作用的
            view.setOnClickListener(v -> {
                Toast.makeText(UIUtils.getContext(),keyword, Toast.LENGTH_SHORT).show();
            });

            flowLayout.addView(view);
        }

        return scrollView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }
}
