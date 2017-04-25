package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.lemon.c.nms.R;

public class NPAFormsActivity extends BaseActivity {


    @Override
    public void widgetClick(View v) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_forms;
    }

    @Override
    protected void initParams(Bundle bundle) {
        setToolbarTitle("NPA报表");
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void doBusiness(final Context mContext) {

    }
}
