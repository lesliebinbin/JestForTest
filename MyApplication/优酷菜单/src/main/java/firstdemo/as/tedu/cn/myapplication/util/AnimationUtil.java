package firstdemo.as.tedu.cn.myapplication.util;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by pc on 2017/3/13.
 */

public class AnimationUtil {
    //旋转出去的动画
    public static void rotateOutAnim(RelativeLayout layout,long delay){
        int childCount = layout.getChildCount();
        for(int i=0;i<childCount;i++){
            layout.getChildAt(i).setEnabled(false);
        }
        RotateAnimation ra = new RotateAnimation(
                0f,-180f,//开始以及结束的角度,逆时针
                Animation.RELATIVE_TO_SELF,0.5f,//相对旋转的坐标(指定旋转中心的x值)
                Animation.RELATIVE_TO_SELF,1.0f//相对y坐标点(指定旋转中心y值)
        );
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setStartOffset(delay);//设置动画开始延时
        ra.setAnimationListener(new MyAnimationListener());
        layout.startAnimation(ra);
    }

    public static void rotateInAnim(RelativeLayout layout,long delay){
        int childCount = layout.getChildCount();
        for(int i=0;i<childCount;i++){
            layout.getChildAt(i).setEnabled(true);
        }
        RotateAnimation ra = new RotateAnimation(
                -180f,0,//开始以及结束的角度,顺时针
                Animation.RELATIVE_TO_SELF,0.5f,//相对旋转的坐标(指定旋转中心的x值)
                Animation.RELATIVE_TO_SELF,1.0f//相对y坐标点(指定旋转中心y值)
        );
        ra.setDuration(500);
        ra.setFillAfter(true);
        ra.setAnimationListener(new MyAnimationListener());
        ra.setStartOffset(delay);//设置动画开始延时
        layout.startAnimation(ra);
    }

    public static int runningAnimationCount = 0;

    static class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            runningAnimationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            runningAnimationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
