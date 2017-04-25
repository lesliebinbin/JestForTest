package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
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
 * Created by pc on 2017/3/12.
 */

public class SwipeLayout extends FrameLayout{

    private View mContentView;//item内容区域的view
    private View mDeleteView;//delete区域的view
    private int mDeleteHeight;
    private int mDeleteWidth;
    private int mContentWidth;
    private int mContentHeight;
    private ViewDragHelper mViewDragHelper;


    public SwipeLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    enum SwipeState{
        Open,Closed;
    }

    private SwipeState currentState = SwipeState.Closed;

    private void init(){
        mViewDragHelper = ViewDragHelper.create(this,mCallback);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDeleteHeight = mDeleteView.getMeasuredHeight();
        mDeleteWidth = mDeleteView.getMeasuredWidth();
        mContentWidth = mContentView.getMeasuredWidth();
        mContentHeight = mContentView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mDeleteView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContentView.layout(0,0,mContentWidth,mContentHeight);
        mDeleteView.layout(mContentView.getRight(),0,mDeleteWidth+mContentView.getRight(),mDeleteHeight);
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==mContentView||child==mDeleteView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDeleteWidth;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(child==mContentView){
                if(left>0){
                    left=0;
                }
                if(left<-mDeleteWidth){
                    left = -mDeleteWidth;
                }
            }else if(child == mDeleteView){
                if(left>mContentWidth){
                    left = mContentWidth;
                }
                if(left<mContentWidth-mDeleteWidth){
                    left = mContentWidth-mDeleteWidth;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if(changedView== mContentView){
                //手动移动deleteView
                mDeleteView.layout(mDeleteView.getLeft()+dx,mDeleteView.getTop()+dy
                        ,mDeleteView.getRight()+dx,mDeleteView.getBottom()+dy);
            }else if(changedView==mDeleteView){
                mContentView.layout(mContentView.getLeft()+dx,mContentView.getTop()+dy
                        ,mContentView.getRight()+dx,mContentView.getBottom()+dy);
            }
            //判断开和关的逻辑
            if(mContentView.getLeft()==0&&currentState!=SwipeState.Closed){
                //说明应该将state更改为关闭

                currentState = SwipeState.Closed;
                if(mOnSwipeStateChangedListener!=null){
                    //回调接口关闭的方法
                    mOnSwipeStateChangedListener.onClosed(getTag());
                }

                //既然SwipeLayout已经关闭,需要让Manager清空一下
                SwipeLayoutManager.getInstance().clearCurrentLayout();
            }else if(mContentView.getLeft()==-mDeleteView.getWidth()&&currentState!=SwipeState.Open){
                //说明应该将state更改为打开
                currentState = SwipeState.Open;

                //回调接口打开的方法
                if(mOnSwipeStateChangedListener!=null){
                    mOnSwipeStateChangedListener.onOpen(getTag());
                }

                //当前的SwipeLayout已经打开,需要让Manager记录一下
                SwipeLayoutManager.getInstance().setSwipeLayout(SwipeLayout.this);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if(mContentView.getLeft()<-mDeleteWidth/2){
                //应该打开
                open();
            }else{
                //应该关闭
                close();
            }
        }
    };

    /**
     *关闭的方法
     */
    public void close() {
        mViewDragHelper.smoothSlideViewTo(mContentView,0,mContentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    /**
     * 打开的方法
     */
    public void open() {
        mViewDragHelper.smoothSlideViewTo(mContentView,-mDeleteWidth,mContentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = mViewDragHelper.shouldInterceptTouchEvent(ev);

        //如果当前有打开的,则需要直接拦截,交给onTouch处理
        if(!SwipeLayoutManager.getInstance().isShouldSwipe(this)){
            result = true;
            //先关闭已经打开的Layout
            SwipeLayoutManager.getInstance().closeCurrentLayout();

        }
        return result;
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
        super.computeScroll();
    }

    private float downX,downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //如果当前有打开的,则下面的逻辑不能执行
        if(!SwipeLayoutManager.getInstance().isShouldSwipe(this)){
            requestDisallowInterceptTouchEvent(true);
            return true;
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                //1.获取x和y移动的距离
                float moveX = event.getX();
                float moveY = event.getY();
                float deltaX = moveX-downX;//X方向移动的距离
                float deltaY = moveY-downY;//Y方向移动的距离
                if(Math.abs(deltaX)>Math.abs(deltaY)){
                    //表示移动是偏向于水平方向,那么SwipeLayout应该处理,请求ListView不要拦截
                    requestDisallowInterceptTouchEvent(true);
                }
                //更新downX,downY
                downX = moveX;
                downY = moveY;
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private OnSwipeStateChangedListener mOnSwipeStateChangedListener;
    public void setOnSwipeStateChangedListener(OnSwipeStateChangedListener listener){
        mOnSwipeStateChangedListener = listener;
    }

    public interface OnSwipeStateChangedListener{
        void onOpen(Object tag);
        void onClosed(Object tag);
    }
}
