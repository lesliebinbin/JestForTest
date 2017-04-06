package leslie.binbin.cn.googleplay.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.http.HttpHelper;
import leslie.binbin.cn.googleplay.utils.BitmapHelper;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * 首页轮播条
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager mViewPager;

    private ArrayList<String> mData;
    private LinearLayout llContainer;

    private int mPreviousPos;//上个圆点位置

    @Override
    public View initView() {
        //创建根布局,相对布局

        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        //初始化布局参数,根布局上层控件是ListView,所以使用ListView定义的LayoutParams
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,UIUtils.dip2px(180)
        );

        rlRoot.setLayoutParams(params);

        //ViewPager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
          RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT
        );

        rlRoot.addView(mViewPager,vpParams);//把viewPager添加到相对布局

        //初始化指示器
        llContainer = new LinearLayout(UIUtils.getContext());
        llContainer.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //设置内边距
        int padding = UIUtils.dip2px(10);

        llContainer.setPadding(padding,padding,padding,padding);

        //添加规则,设置展示位置

        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_END);

        //添加布局
        rlRoot.addView(llContainer,llParams);

        return rlRoot;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        //填充ViewPager的数据
        mData = data;

        mViewPager.setAdapter(new HomeHeaderAdapter());
        mViewPager.setCurrentItem(data.size()*10000);

        //初始化指示器
        for(int i=0;i<data.size();i++){
            ImageView point = new ImageView(UIUtils.getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT
            );

            if(i==0){//第一个默认选中
                point.setImageResource(R.drawable.indicator_selected);
            }else{
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dip2px(4);
            }

            point.setLayoutParams(params);

            llContainer.addView(point);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                position %= data.size();

                //当前被选中的点
                ImageView point = (ImageView) llContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                //上个点变为不选中
                ImageView prePoint = (ImageView) llContainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);

                mPreviousPos = position;

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        HomeHeaderTask task = new HomeHeaderTask();
        //启动轮播条,自动播放
        task.start();
    }

    class HomeHeaderTask implements Runnable{
        public void start(){
            //移除之前发送的所有消息,避免消息重复
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,3000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
            //继续发消息,实现内循环
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter{

        private final BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter(){
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
            //return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= mData.size();
            String url = mData .get(position);
            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(view, HttpHelper.URL+"image?name="+url);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
