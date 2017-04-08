package leslie.binbin.cn.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.utils.UIUtils;

import static android.view.View.MeasureSpec.getSize;

/**
 * Created by pc on 2017/4/8.
 */

public class MyFlowLayout extends ViewGroup {

    private int mUsedWidth;//当前行已使用的宽度
    private int mHorizontalSpacing = UIUtils.dip2px(6);
    private int mVerticalSpacing = UIUtils.dip2px(8);

    private Line mLine;

    private ArrayList<Line> mLineList = new ArrayList<>();//维护所有行的集合

    private static final int MAX_LINE = 100;//最大行数

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = l+getPaddingLeft();
        int top = t+getPaddingTop();

        //遍历所有行对象,设置每行位置
        for(int i=0;i<mLineList.size();i++){
            Line line = mLineList.get(i);
            line.layout(left,top);

            top+=line.mMaxHeight+mVerticalSpacing;//更新top值
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取有效宽度
        int width = getSize(widthMeasureSpec)
                - getPaddingLeft() - getPaddingRight();


        //获取有效高度
        int height = getSize(heightMeasureSpec)
                -getPaddingBottom()-getPaddingTop();
        //获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();//获取所有子控件的数量
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);

            //如果控件是确定模式,子控件包裹内容,否则子控键和父控件一致
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    widthMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:widthMode);

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    heightMode==MeasureSpec.EXACTLY?MeasureSpec.AT_MOST:heightMode);
            //开始测量
            childView.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            //开始测量
            int childWidth = childView.getMeasuredWidth();

            //如果当前行对象,初始化
            if(mLine == null){
                mLine = new Line();
            }

            mUsedWidth+=childWidth;//当前已使用宽度要增加一个子控件

            if(mUsedWidth<width){//是否超出了边界
                mLine.addView(childView);//更多当前行对象添加子控件

                mUsedWidth += mHorizontalSpacing;//增加一个水平间距

                if(mUsedWidth>width){
                    //增加水平键距之后,就超出了边界,此时需要换行
                    if(!newLine()){
                        break;//如果创建行失败,就结束循环,不再添加
                    }
                }
            }else{
                //已超出边界
                if(mLine.getChildCount()==0){
                    //1.当前没有任何空间,一旦添加当前子控件,就超出次间距(子控件很长)
                    mLine.addView(childView);//强制添加到当前行

                    if(!newLine()){//换行的方法
                        break;
                    }
                }else{
                    //2.当前有空间,一旦添加,超出边界
                    if(!newLine()){
                        break;
                    }

                    mLine.addView(childView);
                    mUsedWidth+=childWidth+mHorizontalSpacing;//更新已使用宽度
                }
            }
        }

        //保存最后一行的行对象
        if(mLine!=null&&mLine.getChildCount()!=0&&mLineList.contains(mLine)){
            mLineList.add(mLine);
        }

        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);//控件的整体宽度

        int totalHeight = 0;
        for(int i=0;i<mLineList.size();i++){
            Line line = mLineList.get(i);
            totalHeight+=line.mMaxHeight;
        }


        totalHeight+=(mLineList.size()-1)*mVerticalSpacing;//增加竖直间距

        totalHeight+=getPaddingTop()+getPaddingBottom();//增加上下边距

        //根据最新的宽高来测量整体布局的大小
        setMeasuredDimension(totalWidth,totalHeight);
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    //换行
    private boolean newLine(){
        mLineList.add(mLine);//保存上一行的数据


        if(mLineList.size()<MAX_LINE){
            //可以继续添加
            mLine = new Line();
            mUsedWidth = 0;//已使用宽度清零

            return true;
        }

        return false;

    }

    //每一行的对象封装
    class Line{

        private int mTotalWidth;//当前所有控件总宽度
        public int mMaxHeight;//当前控件的高度(以最高的控件为准)
        private ArrayList<View> myChildViewList = new ArrayList<>();//当前行所有子控件集合


        //添加一个子控件
        public void addView(View view){
            myChildViewList.add(view);
            //总宽度增加
            mTotalWidth+=view.getMeasuredWidth();

            int height = view.getMeasuredHeight();

            //0 10 20 10
            mMaxHeight = mMaxHeight<height?height:mMaxHeight;

        }

        public int getChildCount(){
            return myChildViewList.size();
        }

        //子控件位置设置
        public void layout(int left,int top){
            int childCount = getChildCount();

            //将剩余空间分配给每一个子控件
            //有效宽度
            int validWith = getMeasuredWidth() -
                    getPaddingLeft() - getPaddingRight();
            //计算剩余宽度
            int surplusWidth = validWith-mTotalWidth
                    -(getChildCount()-1)*mHorizontalSpacing;

            if(surplusWidth>=0){
                //有剩余的空间
                int avgSpace = (int) ((float)surplusWidth/childCount+0.5f);

                //重新测量子控件
                for(int i=0;i<childCount;i++){
                    View childView = myChildViewList.get(i);

                    int measureWidth = childView.getMeasuredWidth();
                    int measureHeight = childView.getMeasuredHeight();
                    measureWidth+=avgSpace;

                    int widthSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
                    int heightSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);

                    //重新测量控件
                    childView.measure(widthSpec,heightSpec);

                    //当控件比较矮的时候需要居中展示,竖直方向需要向下有一定的偏移
                    int topOffsize = (mMaxHeight-measureHeight)/2;

                    if(topOffsize<0){
                        topOffsize=0;
                    }

                    //设置控件的位置
                    childView.layout(left,
                            top+topOffsize,
                            left+measureWidth,
                            top+topOffsize+measureHeight);
                    left+=measureWidth+mHorizontalSpacing;//更新left值
                }

            }else{
                //这个控件很长,沾满整行
                View childView = myChildViewList.get(0);
                childView.layout(left,top,
                        left+childView.getMeasuredWidth(),
                        top+childView.getMeasuredHeight());
            }

        }

    }

}
