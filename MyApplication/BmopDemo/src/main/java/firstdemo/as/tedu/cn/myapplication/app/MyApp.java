package firstdemo.as.tedu.cn.myapplication.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;
import firstdemo.as.tedu.cn.myapplication.constant.Const;

/**
 * Created by pc on 2017/3/10.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, Const.BMOB_KEY);
        //初始化ImageLoader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
}
