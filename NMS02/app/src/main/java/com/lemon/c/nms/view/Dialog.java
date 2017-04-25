package com.lemon.c.nms.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

/**
 * Created by C on 2016/8/24.
 */
public class Dialog {


    public static void showDialog(Context context, List<String> list, final TextView tv, String title){
        String[] arr = new String[list.size()];
        list.toArray(arr);
        new MaterialDialog.Builder(context)
                .title(title)
                .items(arr)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        tv.setText(text);
                    }
                })
                .positiveText("取消")
                .show();
    }
}
