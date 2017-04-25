package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 当SlidingMenu打开的时候,拦截并消费掉触摸事件
 * Created by pc on 2017/3/11.
 */

public class MyLinearLayout extends LinearLayout {
    private SlideMenu mSlideMenu;
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSlideMenu(SlideMenu menu){
        mSlideMenu = menu;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mSlideMenu!=null&&mSlideMenu.getCurrentState()== SlideMenu.DragState.Open){
            //如果SlideMenu打开则应该拦截并消费事件
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mSlideMenu!=null&&mSlideMenu.getCurrentState()== SlideMenu.DragState.Open){
            if(event.getAction()==MotionEvent.ACTION_UP){
                //抬起则应关闭
                mSlideMenu.close();
            }

            return true;
        }
        return super.onTouchEvent(event);
    }
}
