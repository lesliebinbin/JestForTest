package com.lemon.c.nms.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lemon.c.nms.R;
import com.lemon.c.nms.utils.SPUtils;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView username;

    private Button main_CM,main_alert,main_CMTS,main_NPA,main_spectrum,main_timeSpectrum,main_DCT,main_forms,main_UPS;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.main_CM:
                startActivity(new Intent(MainActivity.this,CMInfoActivity.class));
                break;
            case R.id.main_alert:
                startActivity(new Intent(MainActivity.this,AlertInfoActivity.class));
                break;
            case R.id.main_CMTS:
                startActivity(new Intent(MainActivity.this,CMTSInfoActivity.class));
                break;
            case R.id.main_NPA:

                break;
            case R.id.main_spectrum:
                startActivity(new Intent(MainActivity.this,SpectrumActivity.class));
                break;
            case R.id.main_timeSpectrum:
                startActivity(new Intent(MainActivity.this,TimeSpectrumActivity.class));
                break;
            case R.id.main_DCT:
                startActivity(new Intent(MainActivity.this,DCTInfoActivity.class));
                break;
            case R.id.main_forms:
                startActivity(new Intent(MainActivity.this,NPAFormsActivity.class));
                break;
            case R.id.main_UPS:
                showToast("开发中...");
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParams(Bundle bundle) {
        showClassic(false);
        setToolbarTitle("天威综合网管系统");
        showMenuIcon(true);
    }

    @Override
    public void initView(View view) {
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.nav_view);
        if(mNavigationView != null){
            setupDrawerContent(mNavigationView);
        }
        removeNavigationViewScrollbar(mNavigationView);
        //headView
        View headView = mNavigationView.getHeaderView(0);
        username =(TextView) headView.findViewById(R.id.username);
        String name = (String) SPUtils.get(this,LoginActivity.USERNAME,"username");
        if(!name.equals("username")){
            username.setText(name);
        }

        //九个主菜单按钮
        main_CM = $(R.id.main_CM);
        main_alert = $(R.id.main_alert);
        main_CMTS = $(R.id.main_CMTS);
        main_NPA = $(R.id.main_NPA);
        main_spectrum = $(R.id.main_spectrum);
        main_timeSpectrum = $(R.id.main_timeSpectrum);
        main_DCT = $(R.id.main_DCT);
        main_forms = $(R.id.main_forms);
        main_UPS = $(R.id.main_UPS);

        main_CM.setOnClickListener(this);
        main_alert.setOnClickListener(this);
        main_CMTS.setOnClickListener(this);
        main_NPA.setOnClickListener(this);
        main_spectrum.setOnClickListener(this);
        main_timeSpectrum.setOnClickListener(this);
        main_DCT.setOnClickListener(this);
        main_forms.setOnClickListener(this);
        main_UPS.setOnClickListener(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private void removeNavigationViewScrollbar(NavigationView navigationView){
        if (navigationView != null){
            NavigationMenuView navigationMenuView =  (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null){
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menu_CMTS:

                                break;
                            case R.id.menu_SNR:

                                break;
                            case R.id.menu_BER:

                                break;
                            case R.id.menu_CM:

                                break;
                            case R.id.menu_DCT:

                                break;
                            case R.id.menu_NPA:

                                break;
                            case R.id.menu_spectrum:

                                break;
                            case R.id.menu_netInfo:

                                break;
                            case R.id.menu_returnLink:

                                break;
                            case R.id.menu_DCTInput:

                                break;
                            case R.id.menu_setting:
                                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                                break;
                        }

                      //  menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
