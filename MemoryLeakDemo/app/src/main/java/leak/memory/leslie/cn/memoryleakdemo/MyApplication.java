package leak.memory.leslie.cn.memoryleakdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by pc on 2017/4/7.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
