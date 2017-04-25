package com.lemon.c.nms.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lemon.c.nms.R;

/**
 * Created by C on 2016/9/2.
 */
public class DialogUtils {

    public static void showAlert(Context context, String content){
        new MaterialDialog.Builder(context)
                .content(content)
                .positiveText(R.string.ensure)
                .show();
    }
}
