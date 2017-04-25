package firstdemo.as.tedu.cn.myapplication;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by pc on 2017/3/11.
 */

public class SlideMenu extends FrameLayout {
    private View menuView;//菜单的View

    private View mainView;//主界面的View


    private ViewDragHelper mViewDragHelper;
    private int mWidth;
    private float mDragRange;//拖拽范围

    private FloatEvaluator mFloatEvaluator;//float计算器
    private IntEvaluator mIntEvaluator;//int计算器

    public SlideMenu(@NonNull Context context) {
        super(context);
        init();
    }

    //定义状态常量
    enum DragState{
        Open,Close
    }

    private DragState currentState = DragState.Close;//当前SlideMenu的状态默认是关闭的

    /**
     * 获取当前状态
     * @return
     */
    public DragState getCurrentState(){
        return currentState;
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mViewDragHelper = ViewDragHelper.create(this,mCallback);
        mFloatEvaluator = new FloatEvaluator();
        mIntEvaluator = new IntEvaluator();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //简单的异常处理
        if(getChildCount()!=2){
            throw new IllegalArgumentException("SlideMenu can have two and two only children");
        }
        menuView = getChildAt(0);
        mainView = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 该方法在在onMeasure方法执行完之后执行,那么可以在该方法中初始化宽和高
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mDragRange = mWidth*0.6f;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback(){

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==menuView||child==mainView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if(changedView==menuView){

                //让menuView固定住
                menuView.layout(0,0,menuView.getMeasuredWidth(),menuView.getMeasuredHeight());

                //让mainView动起来
                int newLeft = mainView.getLeft()+dx;
                if(newLeft<0) newLeft = 0;
                if(newLeft>mDragRange) newLeft = (int) mDragRange;

                mainView.layout(newLeft,mainView.getTop()+dy
                        ,newLeft+mainView.getMeasuredWidth(),mainView.getBottom()+dy);
            }

            //1.计算滑动的百分比
            float fraction = mainView.getLeft()/mDragRange;
            //2.执行伴随的动画
            executeAnim(fraction);
            //3.更改状态回调listener的方法
            if(fraction==0f &&currentState!=DragState.Close){
                currentState=DragState.Close;
                if(mListener!=null){
                    //更改状态为关闭,并回调关闭的方法
                    mListener.onClose();
                }
                //切记不可大意用到==1f这个条件,因为fraction也是float不一定可以达到1f
            }else if(fraction>0.999&&currentState!=DragState.Open){
                currentState=DragState.Open;
                if(mListener!=null){
                    mListener.onOpen();
                }
            }
            //将fraction暴露给外界
            if(mListener!=null){
                mListener.onDraging(fraction);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if(mainView.getLeft()<mDragRange/2){
                //在左边
                close();
            }else{
                //在右边
                open();
            }

            //处理用户稍微滑动
            if(xvel>200&&currentState!=DragState.Open){
                open();
            }else if(xvel<-200&&currentState!=DragState.Close){
                close();
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return (int) mDragRange;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(child == mainView) {
                if (left < 0) {//限制mainView的左边
                    left = 0;
                } else if (left > getViewHorizontalDragRange(child)) {//限制
                    left = getViewHorizontalDragRange(child);
                }
            }
            return left;
        }
    };

    public void open() {
        mViewDragHelper.smoothSlideViewTo(mainView, (int) mDragRange,mainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SlideMenu.this);

    }

    public void close() {
        mViewDragHelper.smoothSlideViewTo(mainView,0,mainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SlideMenu.this);

    }


    /**
     * 执行伴随动画
     * @param fraction
     */
    private void executeAnim(float fraction) {
        //fraction:0-1
        //缩小mainView
        mainView.setScaleX(mFloatEvaluator.evaluate(fraction,1,0.8f));
        mainView.setScaleY(mFloatEvaluator.evaluate(fraction,1,0.8f));
        //移动menuView
        menuView.setTranslationX(mIntEvaluator.evaluate(fraction,
                -menuView.getMeasuredWidth(),0));
        //放大menuView
        menuView.setScaleX(mFloatEvaluator.evaluate(fraction,0.5,1));
        menuView.setScaleY(mFloatEvaluator.evaluate(fraction,0.5,1));
        //透明度
        menuView.setAlpha(mFloatEvaluator.evaluate(fraction,0.2,1));

        //给这个SlideMenu背景添加一个黑色的遮罩效果
        getBackground().setColorFilter((Integer) ColorUtil.evaluateColor(fraction, Color.BLACK
                , Color.TRANSPARENT), PorterDuff.Mode.SRC_OVER);
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private OnDragStateChangedListener mListener;
    public void setOnDragStateChangedListener(OnDragStateChangedListener listener){
        mListener = listener;
    }

    public interface OnDragStateChangedListener{
        /**
         * 打开的回调
         */
        void onOpen();

        /**
         * 关闭的回调
         */
        void onClose();

        /**
         * 拖拽中的回调
         */
        void onDraging(float fraction);
    }
}
