package leslie.binbin.cn.okhttpdemo;

import android.util.Log;

/**
 * Created by pc on 2017/3/31.
 */

public class Leslie {

    private static String TAG = " ";
    private static boolean DEBUG = true;

    public static void e(String msg){
        if(DEBUG) {
            Log.d(TAG, "e() called with: msg = [" + msg + "]");
        }
    }
}

