package leslie.binbin.cn.googleplay.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import leslie.binbin.cn.googleplay.global.GooglePlayApplication;

/**
 * Created by pc on 2017/4/1.
 */

public class UIUtils {

    public static Context getContext(){
        return GooglePlayApplication.getContext();
    }

    public static Handler getHandler(){
        return GooglePlayApplication.getHandler();
    }

    public static int getMainThreadId(){
        return GooglePlayApplication.getMainThread();
    }

    //获取字符串资源
    public static String getString(int resId){
        return getContext().getResources().getString(resId);
    }

    //获取字符串数组资源
    public static String[] getStringArray(int resId){
        return getContext().getResources().getStringArray(resId);
    }

    //获取图片资源
    public static Drawable getDrawable(int resId){
        return getContext().getResources().getDrawable(resId);
    }

    //获取颜色资源
    public static int getColor(int resId){
        return getContext().getResources().getColor(resId);
    }

    //根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(int redId) {
        return getContext().getResources().getColorStateList(redId);
    }

    //获取尺寸资源,返回具体的像素值
    public static int getDimen(int resId){
        return getContext().getResources().getDimensionPixelSize(resId);
    }

    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }

    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density);
    }

    //加载布局文件

    public static View inflate(int resId){
        return LayoutInflater.from(getContext()).inflate(resId,null);
    }

    //判断一个项目是否运行在主线程
    public static boolean isRunOnUIThread(){
        int currentThread = android.os.Process.myTid();
        return currentThread == GooglePlayApplication.getMainThread();
    }

    public static void runOnUIThread(Runnable runnable){
        if(isRunOnUIThread()){
            //已经是主线程直接运行
            runnable.run();
        }else{
            //如果是子线程,借助handler让其运行在主线程
            getHandler().post(runnable);
        }
    }


}
