package leslie.binbin.cn.myapplication;

import android.content.Context;
import android.widget.FrameLayout;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EViewGroup;

/**
 * Created by pc on 2017/4/2.
 * 根据当前状态显示当前不同页面的自定义控件
 *-未加载
 *-加载中
 *  -加载失败
 *  -数据为空
 *  -加载成功
 */
@EViewGroup
public class LoadingPage extends FrameLayout {


    public LoadingPage(Context context) {
        super(context);
    }


    @Background
    public void testForAnnotation(){
        System.out.println("当前线程是:"+Thread.currentThread().getName());
    }

}
