package com.lemon.c.nms.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.c.nms.R;
import com.lemon.c.nms.appmanage.AppManager;

/**
 * Created by C on 2016/7/25.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    //是否沉浸状态栏
    private boolean isSetStatusBar = true;
    //是否全屏显示
    private boolean isAllowFullScreen;
    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = false;
    //当前activity的渲染试图View
    private View mContextView = null;
    //日志输出标志
    protected final String TAG = this.getClass().getSimpleName();

    public abstract void widgetClick(View v);

    private boolean isShowback = true;
    private boolean isMenu = false;
    private boolean isClassic = true;
    private String mTitle ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        initParams(bundle);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(),null);
        setContentView(mContextView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        if(toolbar != null) {

            if(isClassic){
                toolbar.setTitle(mTitle);
            } else {
                toolbar.setTitle("");
                TextView title = (TextView) findViewById(R.id.toolbar_title);
                title.setText(mTitle);
            }
            setSupportActionBar(toolbar);
            final ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(isShowback);
            if(isMenu){
               ab.setHomeAsUpIndicator(R.drawable.icon_menu);
            }
        }


        if(isAllowFullScreen){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if(isSetStatusBar){
            steepStatusBar();
        }

        if(!isAllowScreenRoate){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initView(mContextView);
        setListener();
        doBusiness(this);
        //添加到activity 任务栈
        AppManager.getAppManager().addActivity(this);
    }

    private void steepStatusBar() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    //绑定布局
    public abstract  int bindLayout();

    //初始化参数
    protected abstract void initParams(Bundle bundle);

    //初始化控件
    public abstract void initView(final View view);

    //绑定控件
    protected    <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    //设置监听
    public abstract void setListener();

    @Override
    public void onClick(View view) {
        widgetClick(view);
    }

    public abstract void doBusiness(Context mContext);


    //页面跳转 不含数据
    public void startActivity(Class<?> clz){
        startActivity(new Intent(BaseActivity.this,clz));
    }

    //页面跳转 含有数据
    public void startActivity(Class<?> clz,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this,clz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        AppManager.getAppManager().delectActivity(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    //简化toast
    protected  void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    public void setToolbarTitle(String str){
        this.mTitle = str;
    }

    public void showBackIcon(boolean isShow){
        this.isShowback = isShow;
    }

    public void showMenuIcon(boolean isMenu){this.isMenu = isMenu;}

    public void showClassic(boolean isClassic){this.isClassic = isClassic;}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
