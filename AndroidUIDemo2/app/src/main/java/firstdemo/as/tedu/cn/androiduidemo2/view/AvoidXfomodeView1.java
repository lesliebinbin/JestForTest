package firstdemo.as.tedu.cn.androiduidemo2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pc on 2017/3/16.
 */

public class AvoidXfomodeView1 extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private PorterDuffXfermode mPorterDuffXfermode;

    public AvoidXfomodeView1(Context context) {
        super(context);
    }

    public AvoidXfomodeView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvoidXfomodeView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }
}
