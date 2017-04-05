package leslie.binbin.cn.googleplay.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by pc on 2017/4/5.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        this.setSelector(new ColorDrawable());//设置背景状态,默认状态选择为全透明
        this.setDivider(null);
        this.setCacheColorHint(Color.TRANSPARENT);//有时候滑动ListView
        //背景会变成黑色,此方法可以将背景变成全透明
    }


}
