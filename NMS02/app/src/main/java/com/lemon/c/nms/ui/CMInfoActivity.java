package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.lemon.c.nms.R;

public class CMInfoActivity extends BaseActivity {
    @Override
    public void widgetClick(View v) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_cminfo;
    }

    @Override
    protected void initParams(Bundle bundle) {
        setToolbarTitle("CM信息查询");
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
