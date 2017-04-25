package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by pc on 2017/3/12.
 */

public class ParallaxListView extends ListView{
    private ImageView mImageView;
    private int maxHeight;
    private int mOriginalHeight;//imageView最初的高度

    public void setParallaxImageView(ImageView imageView){
        mImageView = imageView;


        //设定最大高度


        //int originalHeight = mImageView.getHeight();//获取imageView的高度,但是注意
        //getHeight()这里是由坑的,因为getHeight可能是0,所以要么像getMeasuredHeight或者
        //在以下这个方法内去执行getHeight

        mImageView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        mOriginalHeight = mImageView.getHeight();
                        int drawableHeight = mImageView.getDrawable().getIntrinsicHeight();
                        maxHeight = mOriginalHeight >drawableHeight? mOriginalHeight *2:drawableHeight;//最大的图片的高度
                        //上面注意,mImageView的高度不一定是图片的高度
                    }
                }
        );


    }
    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 这个方法在listView滑动到头的时候执行,可以获取到继续滑动的距离和方向
     * @param deltaX:继续滑动x方向的距离
     * @param deltaY:继续滑动y方向的距离:负值表示顶部到头了,正直表示底部到头了
     * @param scrollX:
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX:x方向最大可以滚动的距离
     * @param maxOverScrollY:y方向最大可以滚动的距离
     * @param isTouchEvent:true 是否是手指拖动滑动 false:表示fling靠惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(deltaY<0&&isTouchEvent){
            //表示顶部到头了,并且是手动拖动到头的一个情况
            //我们需要不断的增加ImageView的一个高度
            int newHeight = mImageView.getHeight()-deltaY/3;
            if(newHeight>maxHeight){
                newHeight=maxHeight;
            }
            if(mImageView!=null) {
                mImageView.getLayoutParams().height = newHeight;
                mImageView.requestLayout();//使imageView的布局参数生效
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            //需要将ImageView的高度缓慢恢复到最初高度
            ValueAnimator animator = ValueAnimator.ofInt(mImageView.getHeight(),mOriginalHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获取动画的值,设置给imageView
                    int animatedValue = (int) valueAnimator.getAnimatedValue();
                    mImageView.getLayoutParams().height = animatedValue;
                    mImageView.requestLayout();
                }
            });
            animator.setDuration(350);
            animator.setInterpolator(new OvershootInterpolator(5));//弹性的插值器
            animator.start();
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
