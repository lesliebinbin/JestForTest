package leslie.binbin.cn.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * 自定义application 进行全局初始化
 * @author Leslie
 * @date 2017/3/31.
 */
public class GooglePlayApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mainThread;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        mHandler = new Handler();

        //当前线程id,主线程id
        mainThread = Process.myTid();
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThread() {
        return mainThread;
    }
}
