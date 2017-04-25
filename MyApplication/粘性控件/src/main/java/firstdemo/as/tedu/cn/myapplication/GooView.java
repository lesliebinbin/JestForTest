package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by pc on 2017/3/13.
 */

public class GooView extends View {

    private Paint mPaint;

    public GooView(Context context) {
        super(context);
        init();

    }

    public GooView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GooView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        mPaint.setColor(Color.RED);
    }

    private float dragRadius = 12f;//拖拽圆的半径
    private float stickRadius = 12f;//固定圆的半径
    private PointF dragCenter = new PointF(100f,120f);//拖拽圆的圆心
    private PointF stickyCenter = new PointF(150f,120f);//固定圆的圆心

    private PointF[] stickyPoint = {new PointF(150f,108f),new PointF(150f,132f)};
    private PointF[] dragPoint = {new PointF(100f,108f),new PointF(100f,132f)};

    private PointF controlPoint = new PointF(125f,120f);

    private double lineK;//斜率
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //让整体画布往上偏移
        canvas.translate(0,-Utils.getStatusBarHeight(getResources()));


        stickRadius = getStickRadius();

        //dragPoint:两圆心连线的垂线与圆的交点
        //sticktyPoint:两圆心连线的垂线与圆的交点
        // 根据dragCenter动态求出dragPointer和stickyPointer
        float xOffSet = dragCenter.x-stickyCenter.x;
        float yOffset = dragCenter.y-stickyCenter.y;
        if(xOffSet!=0){
            lineK = yOffset/xOffSet;
        }

        dragPoint = GeometryUtil.getIntersectionPoints(dragCenter,dragRadius,lineK);
        stickyPoint = GeometryUtil.getIntersectionPoints(stickyCenter,stickRadius,lineK);

        //动态计算控制点

        controlPoint = GeometryUtil.getPointByPercent(dragCenter,stickyCenter,0.618f);

        //1.绘制两个圆
        canvas.drawCircle(dragCenter.x,dragCenter.y,dragRadius,mPaint);//绘制拖拽圆

        if(!isDragOutOfRange) {
            canvas.drawCircle(stickyCenter.x,stickyCenter.y,stickRadius,mPaint);//绘制固定圆
            //2.使用贝塞尔曲线绘制连接部分
            Path path = new Path();
            path.moveTo(stickyPoint[0].x, stickyPoint[0].y);//设置起点
            path.quadTo(controlPoint.x, controlPoint.y, dragPoint[0].x, dragPoint[0].y);//使用贝塞尔曲线连接
            path.lineTo(dragPoint[1].x, dragPoint[1].y);
            path.quadTo(controlPoint.x, controlPoint.y, stickyPoint[1].x, stickyPoint[1].y);
            //path.close();//默认就是闭合的,这个方法写不写都没关系
            canvas.drawPath(path, mPaint);
        }

        //绘制圈圈,以固定圆圆心为圆心,然后以80为半径
        mPaint.setStyle(Paint.Style.STROKE);//设置只有边线
        canvas.drawCircle(stickyCenter.x,stickyCenter.y,maxDistance,mPaint);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private float maxDistance = 80f;

    /**
     * 动态求出固定圆的半径
     */
    private float getStickRadius(){
        float radius;
        //求出两个圆心之间的距离
        float centerDistance = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
        float fraction = centerDistance/maxDistance;//圆心距离占总距离的百分比
        radius = GeometryUtil.evaluateValue(fraction,12f,4f);
        return radius;
    }

    private boolean isDragOutOfRange = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                isDragOutOfRange = false;
            case MotionEvent.ACTION_MOVE:
                dragCenter.set(event.getRawX(),event.getRawY());
                if(GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter)>maxDistance){
                    //超出范围,应该断掉,不再绘制贝塞尔曲线
                    isDragOutOfRange = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter)>maxDistance){
                    dragCenter.set(stickyCenter.x,stickyCenter.y);
                }else {
                    if(isDragOutOfRange){
                        //如果曾经超出过范围
                        dragCenter.set(stickyCenter.x,stickyCenter.y);
                   }else{
                        //动画的形式回去
                        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(1);
                        final PointF startPointF = new PointF(dragCenter.x,dragCenter.y);

                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                //动画执行的百分比
                                float animatorFraction = valueAnimator.getAnimatedFraction();
                                PointF pointF = GeometryUtil.getPointByPercent(startPointF,stickyCenter,animatorFraction);
                                dragCenter.set(pointF);
                                invalidate();
                            }
                        });
                        valueAnimator.setDuration(500);
                        valueAnimator.setInterpolator(new OvershootInterpolator(10));
                        valueAnimator.start();
                    }
                }
                break;
        }
        invalidate();

        return true;
    }
}
