package leslie.binbin.cn.googleplay.ui.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * 详情页-应用描述
 */

public class DetailDesHolder extends BaseHolder<AppInfo> {

    private TextView tvDes;
    private TextView tvAuthor;
    private ImageView ivArrow;
    private RelativeLayout rlToggle;
    private LinearLayout.LayoutParams mParams;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);

        tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        rlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);

        rlToggle.setOnClickListener(v -> {
            toggle();
        });

        return view;
    }

    private boolean isOpen = false;

    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();

        if (longHeight < shortHeight) return;//如果本身的内容就小于7行,也就没有必要执行什么动画这些屁事了

        ValueAnimator animator = null;
        if (isOpen) {
            //关闭
            isOpen = false;
            animator = ValueAnimator.ofInt(longHeight, shortHeight);
        } else {
            //打开
            isOpen = true;
            animator = ValueAnimator.ofInt(shortHeight, longHeight);
        }

        animator.addUpdateListener(ani -> {
            Integer height = (Integer) ani.getAnimatedValue();
            mParams.height = height;
            tvDes.setLayoutParams(mParams);
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //ScrollView滑动到最底部
                ScrollView scrollView = getScrollView();
                //为了运行更加安全和稳定,可以将滑动到底部方法放到消息队列中,
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                ivArrow.setImageResource(isOpen ? R.drawable.arrow_up : R.drawable.arrow_down);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.setDuration(200);
        animator.start();
    }

    @Override
    public void refreshView(AppInfo data) {
        tvDes.setText(data.des);
        tvAuthor.setText(data.author);
        //post方法主要是用在当你发现你的代码没有问题,但是运行起来就是乖乖的
        //因为不同的机型,有时候系统绘制的速率不一样,有一些比较差的,绘制的比较慢一些
        //但是用了post放到消息队列里面就可以执行的比较流畅
        tvDes.post(() -> {
            int shortHeight = getShortHeight();
            mParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
            mParams.height = shortHeight;
            tvDes.setLayoutParams(mParams);
        });
        //默认展示7行的高度


    }

    /**
     * 获取7行TextView的高度
     */
    private int getShortHeight() {
        //模拟一个textview,设置最大行数为7行,计算虚拟TextView的高度,从而
        //知道tvDes在展示7行时应该多高
        int width = tvDes.getMeasuredWidth();//宽度

        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des);//设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//文字大小一致
        view.setMaxLines(7);//最大行为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变,确定值,填充父窗体
        int heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        //高度包裹内容,wrap_content,当包裹内容时,参1表示尺寸的最大值暂写2000,也可以是屏幕高度
        //开始测量
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();//返回测量后的高度

    }

    //获取ScrollView,一层一层往上找,直到找到ScrollView返回;注意,一定要保证父控件或祖宗空间有ScrollView
    private ScrollView getScrollView() {
        ViewParent parent = tvDes.getParent();

        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }

        return (ScrollView) parent;
    }

    /**
     * 获取完整的TextView的高度
     */
    private int getLongHeight() {
        //模拟一个textview,设置最大行数为7行,计算虚拟TextView的高度,从而
        //知道tvDes在展示7行时应该多高
        int width = tvDes.getMeasuredWidth();//宽度

        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des);//设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//文字大小一致

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变,确定值,填充父窗体
        int heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        //高度包裹内容,wrap_content,当包裹内容时,参1表示尺寸的最大值暂写2000,也可以是屏幕高度
        //开始测量
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();//返回测量后的高度

    }
}
