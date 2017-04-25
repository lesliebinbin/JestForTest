package firstdemo.as.tedu.cn.androiduidemo2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import firstdemo.as.tedu.cn.androiduidemo2.R;

/**
 * Created by pc on 2017/3/16.
 */

public class EmbossMaskFilterView extends View {
    public EmbossMaskFilterView(Context context) {
        super(context);
    }

    public EmbossMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmbossMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] direction = new float[]{1f,1f,3f};//设置光源方向
        float light = 0.4f;//设置环境亮度
        float specular = 8f;//定义镜面反射系数
        float blur = 3.0f;//模糊半径
        EmbossMaskFilter emboss = new EmbossMaskFilter(direction,light,specular,blur);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(70);
        paint.setStrokeWidth(8);
        paint.setMaskFilter(emboss);

        canvas.drawText("最喜欢看曹神日狗了",50,100,paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img1352);
        canvas.drawBitmap(bitmap,150,200,paint);

        setLayerType(View.LAYER_TYPE_HARDWARE,null);
    }

}
