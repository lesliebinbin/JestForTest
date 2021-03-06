package leslie.binbin.cn.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.protocol.HomeDetailProtocal;
import leslie.binbin.cn.googleplay.ui.holder.DetailAppInfoHolder;
import leslie.binbin.cn.googleplay.ui.holder.DetailDesHolder;
import leslie.binbin.cn.googleplay.ui.holder.DetailDownloadHolder;
import leslie.binbin.cn.googleplay.ui.holder.DetailPicsHolder;
import leslie.binbin.cn.googleplay.ui.holder.DetailSafeHolder;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class HomeDetailActivity extends BaseActivity {

    private String packageName;
    private AppInfo mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingPage mLoadingPage = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        setContentView(mLoadingPage);//直接将View对象,设置给Activity

        //获取HomeFragment传递过来的包名
        packageName = getIntent().getStringExtra("packageName");

        //开始加载网络数据
        mLoadingPage.loadData();

        initActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public View onCreateSuccessView() {

        //初始化成功布局

        View view = UIUtils.inflate(R.layout.page_home_detail);

        //初始化应用信息模块
        FrameLayout flDetailAppInfo =
                (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);

        //动态给帧布局页面
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();

        flDetailAppInfo.addView(appInfoHolder.getRootView());

        appInfoHolder.setData(mData);

        //初始化安全描述模块

        FrameLayout flDetailSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DetailSafeHolder safeHolder = new DetailSafeHolder();
        flDetailSafe.addView(safeHolder.getRootView());

        safeHolder.setData(mData);

        //初始化截图模块
        HorizontalScrollView hsvPic = (HorizontalScrollView) view.findViewById(R.id.hsv_details_pics);

        DetailPicsHolder picsHolder = new DetailPicsHolder();
        hsvPic.addView(picsHolder.getRootView());
        picsHolder.setData(mData);

        //初始化描述模块
        FrameLayout flDetailDes = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        DetailDesHolder desHolder = new DetailDesHolder();
        flDetailDes.addView(desHolder.getRootView());
        desHolder.setData(mData);

        //初始化下载布局
        FrameLayout flDetailDownload =
                (FrameLayout) view.findViewById(R.id.fl_detail_download);
        DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
        flDetailDownload.addView(downloadHolder.getRootView());
        downloadHolder.setData(mData);

        return view;
    }

    public LoadingPage.ResultState onLoad() {


        //请求网络,加载数据

        HomeDetailProtocal protocol = new HomeDetailProtocal(packageName);
        mData = protocol.getData(0);
        if (mData != null) {
            return LoadingPage.ResultState.STATE_SUCCESS;
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);//设置home处可以点击
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键
    }
}
