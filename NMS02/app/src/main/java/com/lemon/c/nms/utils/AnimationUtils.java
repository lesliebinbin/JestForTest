package com.lemon.c.nms.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by jsb on 2017/4/25.
 */
public class AnimationUtils {
    /*************实现帧动画画*************/

    /**
     *
     * @param context
     * @param imageView 要实现祯动画的imageView
     * @param animRes 镇动画每一帧的图片资源,可可传多个值
     */
    public static void startFrameAni(Context context, ImageView imageView, int... animRes){
        AnimationDrawable animDrawable = (AnimationDrawable) imageView.getDrawable();
        for(int anim:animRes){
            Drawable drawable = context.getResources().getDrawable(anim);
            animDrawable.addFrame(drawable,200);
        }
        animDrawable.setOneShot(false);
        animDrawable.start();
    }
}
