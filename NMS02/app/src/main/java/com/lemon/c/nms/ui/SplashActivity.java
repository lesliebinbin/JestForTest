package com.lemon.c.nms.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.lemon.c.nms.R;
import com.lemon.c.nms.utils.AnimationUtils;
import com.lemon.c.nms.utils.SPUtils;

public class SplashActivity extends BaseActivity {

    private ImageView loading;

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash2;
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    public void initView(View view) {
         /*实现loadin...*/
        loading = $(R.id.loading);
        AnimationUtils.startFrameAni(this, loading, R.drawable.loading_0, R.drawable.loading_1, R.drawable.loading_2, R.drawable.loading_3);
        /*实现loadin...帧动画效果*/
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        final Intent intent;
        String username = (String) SPUtils.get(this, LoginActivity.USERNAME, "user");
        if (username.equals("user")) {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                /********添加Activity跳转动画效果**********/
                overridePendingTransition(R.anim.main_in_anim, R.anim.splash_out_anim);
                /********添加Activity跳转动画效果**********/
                finish();
            }
        }, 1000);
    }


}

