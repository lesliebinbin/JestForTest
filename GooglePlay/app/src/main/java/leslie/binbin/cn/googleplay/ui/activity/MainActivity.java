package leslie.binbin.cn.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.ui.fragment.BaseFragment;
import leslie.binbin.cn.googleplay.ui.fragment.FragmentFactory;
import leslie.binbin.cn.googleplay.ui.view.PagerTab;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * 当项目和appcompat关联在一起时,就必须在清单文件中设置Theme.AppCompat的主题,否则崩溃
 *
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewById(R.id.viewpager)
    ViewPager mViewPager;
    @ViewById(R.id.pager_tab)
    PagerTab mPagerTab;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyAdapter(getSupportFragmentManager());

    }

    /**
     * 开始使用控件
     * 注意：不要在 onCreate() 方法中调用通过注解得到的控件
     *       因为 onCreate() 在执行时 view 还没有注入，会导致空指针异常
     */
    @AfterViews
    void initView(){
        mViewPager.setAdapter(mAdapter);
        mPagerTab.setViewPager(mViewPager);//将指示器和ViewPager绑定在一起
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment baseFragment = FragmentFactory.createFragment(position);
                //开始加载数据
                baseFragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    class MyAdapter extends FragmentPagerAdapter{

        private String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            //加载页面标题数组
            mTabNames = UIUtils.getStringArray(R.array.tab_names);
        }

        //返回页签标题
        @Override
        public CharSequence getPageTitle(int position) {



            return mTabNames[position];
        }

        //返回当前页面位置的Fragment对象
        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        //Fragment的数量
        @Override
        public int getCount() {
            return mTabNames.length;
        }
    }
}
