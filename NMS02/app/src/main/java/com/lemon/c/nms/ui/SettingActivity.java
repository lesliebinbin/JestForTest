package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import com.lemon.c.nms.R;

/**
 * Created by C on 2016/8/16.
 */
public class SettingActivity extends BaseActivity{
    @Override
    public void widgetClick(View v) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initParams(Bundle bundle) {
        setToolbarTitle("系统设置");
    }

    @Override
    public void initView(View view) {
        getFragmentManager().beginTransaction().add(R.id.setting,new SettingFragment()).commit();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }


    public static class SettingFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
        }
    }
}
