package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by pc on 2017/3/10.
 */

public class DragLayout extends FrameLayout{

    private View mRedView;//红孩子
    private View mBlueView;//蓝孩子

    private ViewDragHelper mViewDragHelper;


    private void init(){
        mViewDragHelper = ViewDragHelper.create(this,mCallback);
    }

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 当DragLayout的xml标签被读取完成,
     * 会执行该方法,此时会知道自己有几个子View了
     * 一般是用来初始化子View的引用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRedView = getChildAt(0);
        mBlueView = getChildAt(1);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //要测量自己的子View
    //int size = getResources().getDimension(R.dimen.width);//100dp
//        int measureSpec = MeasureSpec.makeMeasureSpec(mRedView.getLayoutParams().width,
//                MeasureSpec.EXACTLY);
//        mRedView.measure(measureSpec,measureSpec);
//        mBlueView.measure(measureSpec,measureSpec);

    //以上的方法是可以的,但是太麻烦,如果说没有特殊的对子view的测量需求,可以用
    //如下方法
//        measureChild(mRedView,widthMeasureSpec,heightMeasureSpec);
//        measureChild(mBlueView,widthMeasureSpec,heightMeasureSpec);
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //让mViewDragHelper帮我们判断是否应该拦截
        boolean result = mViewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();

        mRedView.layout(left,top,left+mRedView.getMeasuredWidth(),top+mRedView.getMeasuredHeight());
        mBlueView.layout(left,top+mRedView.getBottom(),left+mBlueView.getMeasuredWidth(),mRedView.getBottom()+mBlueView.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件交给mViewDrager来解析处理
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 用于判断是否用于判断当前child的触摸事件
         * @param child 当前触摸的子View
         * @param pointerId
         * @return  true:就捕获并解析,false不处理
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            return child==mBlueView||child==mRedView;
        }

        /**
         * 当View被开始捕获和解析的回调
         * @param capturedChild 当前被捕获的子View
         * @param activePointerId
         */
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            System.out.println("哈哈,被抓住了吧?");
        }

        /**
         * 获取View水平拖拽的一个范围,但是目前不能限制边界
         * @param child
         * @return  目前用在手指抬起的时候View缓慢移动的动画计算时间上面
         * 最好不要返回0
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth()-child.getMeasuredWidth();
        }

        /**
         * 获取View垂直拖拽一个范围,但是目前不能限制边界
         * @param child
         * @return 最好也不要返回0
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight()-child.getMeasuredHeight();
        }

        /**
         * 控制Child在水平方向的移动
         * @param child
         * @param left:表示ViewDragHelper你想让当前child的left改变的值,
         *            left=child.getLeft()+dx
         * @param dx    本次child水平方向移动的距离
         * @return:表示你想让child的left变成的值
         */


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(left<0){
                return 0;
            }else if(left>getViewHorizontalDragRange(child)){
                return getViewHorizontalDragRange(child);
            }
            return left;
            //return left-dx 这样子这个View就不能移动了
        }

        /**
         * 控制Child在垂直方向的移动
         * @param child
         * @param top:表示ViewDragHelper你想让当前child的left改变的值,
         *            left=child.getLeft()+dx
         * @param dy    本次child水平方向移动的距离
         * @return:表示你想让child的top真正变成的值
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if(top<0){
                return 0;
            }else if(top>getViewVerticalDragRange(child)){
                return getViewVerticalDragRange(child);
            }

            return top;
        }

        /**
         * 当child的位置改变的时候执行,一般用来做其他子view的伴随移动
         * @param changedView 位置改变的child
         * @param left  child当前最新的left
         * @param top   child当前最新的top
         * @param dx    本次移动的距离
         * @param dy    本次垂直移动的距离
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            //System.out.println("left:"+left+" top:"+top+" dx:"+dx+" dy:"+dy);
            if(changedView==mBlueView){
                //mBlueView移动的时候需要让mRedView跟随移动
                mRedView.layout(mRedView.getLeft()+dx,mRedView.getTop()+dy
                        ,mRedView.getRight()+dx,mRedView.getBottom()+dy);
            }else if(changedView==mRedView){
                mBlueView.layout(mBlueView.getLeft()+dx,mBlueView.getTop()+dy
                        ,mBlueView.getRight()+dx,mBlueView.getBottom()+dy);
            }
            //1.计算View移动的百分比
            float fraction = 1f*changedView.getLeft()/getViewHorizontalDragRange(changedView);
            execAnimation(fraction);
        }

        /**
         * 手指抬起的时候执行该方法
         * @param releasedChild:当前抬起的那个View
         * @param xvel:x方向的速度   正值表示向右移动,负值表示向左移动
         * @param yvel:y方向移动的速度 正值表示向下移动,负值表示向上移动
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            int centerLeft = getMeasuredWidth()/2-releasedChild.getMeasuredWidth()/2;
            if(releasedChild.getLeft()<centerLeft){
                //在左半边,应该向左缓慢移动
                mViewDragHelper.smoothSlideViewTo(releasedChild,0,releasedChild.getTop());
            }else{
                //在右边边,应该向右缓慢移动
                mViewDragHelper.smoothSlideViewTo(releasedChild,getViewHorizontalDragRange(
                        releasedChild
                ),releasedChild.getTop());
            }
            ViewCompat.postInvalidateOnAnimation(DragLayout.this);
        }


    };

    /**
     * 执行伴随动画
     * @param fraction
     */
    private void execAnimation( float fraction){
        //缩放动画
        //mRedView.setScaleX(1+0.5f*fraction);
        //mRedView.setScaleY(1+0.5f*fraction);

        //旋转动画
        mRedView.setRotation(720*fraction);
        mBlueView.setRotation(360*fraction);

        //mRedView.setRotationY(360*fraction);

        //平移动画
        //mRedView.setTranslationX(80*fraction);

        //透明
        //mRedView.setAlpha(1-fraction);

        //设置过度颜色的渐变
        mRedView.setBackgroundColor((Integer) ColorUtil.evaluateColor(fraction, Color.RED,Color.GREEN));
    }

    @Override
    public void computeScroll(){
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(DragLayout.this);
        }
    }

}
