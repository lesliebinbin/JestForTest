package leslie.binbin.cn.googleplay.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.protocol.HomeDetailProtocal;
import leslie.binbin.cn.googleplay.ui.holder.DetailAppInfoHolder;
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
    }

    public View onCreateSuccessView(){

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


        return view;
    }

    public LoadingPage.ResultState onLoad() {


        //请求网络,加载数据

        HomeDetailProtocal protocol = new HomeDetailProtocal(packageName);
        mData = protocol.getData(0);
        if(mData !=null){
            return LoadingPage.ResultState.STATE_SUCCESS;
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
