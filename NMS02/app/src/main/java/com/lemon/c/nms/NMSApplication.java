package com.lemon.c.nms;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import okhttp3.OkHttpClient;

/**
 * Created by C on 2016/8/17.
 */
public class NMSApplication extends Application{
    public boolean  login ;
    private static NMSApplication instance;
    public String username;

    @Override
    public void onCreate() {
        super.onCreate();
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public NMSApplication(){}

    public static Context mContext;

    public static NMSApplication getInstance() {
        if (null == instance) {
            instance = new NMSApplication();
        }
        return instance;
    }

    public  String  getVersionInfo(){

        String versionInfo = null;
        try {
            versionInfo = mContext.getPackageManager().getPackageInfo(getPackageName(),0).versionName
                    +"_"+ mContext.getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionInfo;
    }

}
