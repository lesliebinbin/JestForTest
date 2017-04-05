package leslie.binbin.cn.googleplay.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import leslie.binbin.cn.googleplay.R;

/**
 * 自定义控件,按照比例决定高度
 */

public class RatioLayout extends FrameLayout {

    private float ratio;

    public RatioLayout(Context context) {
        super(context);
        initView();
    }

    private void initView() {
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        //获取属性值
        //当自定义一个属性的时候,系统会自动生成属性相关id,此id通过R.styleable来引用
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        //id=属性名_具体字段名称(id此系统自动生成的)
        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();//回收typeArray,提高性能
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //1.获取宽度
        //2.根据ratio计算空间的高度
        //3.重新测量空间

        //MeasureSpec.AT_MOST 至多模式,控件有多大显示多大,wrap_content
        //MeasureSpec.EXACTLY 确定模式,类似于宽高写死 match_parent
        //MeasureSpec.UNSPECIFIED 未确定模式,动态测量

        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度的模式

        int height = MeasureSpec.getSize(heightMeasureSpec);//获取高度值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度的模式

        //宽度确定,高度不确定,比例ratio合法,才计算高度值
        if(widthMode==MeasureSpec.EXACTLY&&heightMode!=MeasureSpec.EXACTLY
                &&ratio>0){
            //图片宽度=控件宽度-左内边距-右内边距
            int imageWidth = width-getPaddingLeft()-getPaddingRight();
            //图片高度 = 图片宽度/宽高比例
            int imageHeight = (int) (imageWidth/ratio+0.5f);

            //控件高度=图片高度+上侧内边距+下侧内边距
            height = imageHeight+getPaddingTop()+getPaddingBottom();

            //根据最新的高度来重新生成heightMeasureSpec(测量后高度模式已经确定了)
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        //按照最新的模式去测量控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
