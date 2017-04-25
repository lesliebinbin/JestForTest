package firstdemo.as.tedu.cn.androiduidemo2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pc on 2017/3/16.
 */

public class PorterDuffXfermodeView extends View {
    private Paint mPaint;
    private PorterDuffXfermode mPorterDuffXfermode;
    public PorterDuffXfermodeView(Context context) {
        super(context);
    }

    public PorterDuffXfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PorterDuffXfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

        //设置背景颜色
        canvas.drawARGB(255,139,197,186);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0,0,canvasWidth,canvasHeight,
                null,Canvas.ALL_SAVE_FLAG);
        int r = canvasWidth/3;

        //正常绘制黄色的圆形
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(r,r,r,mPaint);
        //使用Clear作为PorterDuffXformode绘制蓝色的矩形
        mPaint.setXfermode(mPorterDuffXfermode);
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(r,r,r*2.7f,r*2.7f,mPaint);

        //最后将画笔去除xfermode
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
