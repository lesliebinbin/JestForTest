package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pc on 2017/3/12.
 */

public class QuickIndexBar extends View{

    private Paint mPaint;
    private float cellHeight;
    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    private int mWidth;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.CENTER);//设置文本的起点是文字边框的中心
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        //得到一个格子的高度
        cellHeight = 1f*getMeasuredHeight()/indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<indexArr.length;i++){
            float x = mWidth/2;
            //获取文本高度

            mPaint.setColor(lastIndex==i?Color.BLACK:Color.WHITE);
            float y = cellHeight/2+getTextHeight(indexArr[i])/2+cellHeight*i;
            canvas.drawText(indexArr[i],x,y,mPaint);
        }
    }

    private int lastIndex = -1;//记录上次触摸字母的索引
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y/cellHeight);//得到字母对应的索引
                if(lastIndex!=index){
                    //说明当前触摸与上一个不是同一个字母
                    //对index做安全性的检查
                    if(index>=0&&index<indexArr.length){
                        if(mOnLetterTouchListener!=null){
                            mOnLetterTouchListener.onTouchLetter(indexArr[index]);
                        }
                    }

                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                //重置lastIndex
                lastIndex = -1;
                break;
        }

        //引起重绘
        invalidate();
        return true;
    }

    /**
     * 获取文本高度
     * @param text
     * @return
     */

    private int getTextHeight(String text){
        //获取文本高度
        Rect bounds = new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds);
        return bounds.height();
    }

    private OnLetterTouchListener mOnLetterTouchListener;

    public void setOnLetterTouchListener(OnLetterTouchListener listener){
        mOnLetterTouchListener = listener;
    }

    /**
     * 触摸字母的监听器
     */
    public interface OnLetterTouchListener{
        void onTouchLetter(String letter);
    }

}
