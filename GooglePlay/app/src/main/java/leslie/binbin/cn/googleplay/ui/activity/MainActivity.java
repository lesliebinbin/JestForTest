package leslie.binbin.cn.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

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
    private ActionBarDrawerToggle mToggle;

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
        initActionBar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //切换抽屉
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    //初始化ActionBar
    private void initActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);//设置home处可以点击
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键
        //当和侧边栏结合时展示三个杠图片

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //抽屉的开关
        //参2:DrawerLayout对象,参3:左上角图片
        mToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_drawer_am,
                R.string.drawer_open,
                R.string.drawer_close);
                //参4:打开侧边栏描述
                //参5:关闭侧边栏描述

        mToggle.syncState();//同步状态,将DrawLayout和开关关联在一起
    }
}
