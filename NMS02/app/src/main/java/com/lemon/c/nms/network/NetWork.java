package com.lemon.c.nms.network;

import android.content.Context;
import android.os.Build;

import com.lemon.c.nms.ui.LoginActivity;
import com.lemon.c.nms.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by C on 2016/8/29.
 */
public class NetWork {

    public static String PAGE_SIZE = "20";
    public static String ERROR = "error";

    public static String getAlertData(Context context, int alertType, int alertLevel, int pageIndex) {
        String username = (String) SPUtils.get(context, LoginActivity.USERNAME, "");
        String addUrl = "";
        switch (alertType) {
            case 0:
                addUrl = URLs.ALARM_SNR;
                break;
            case 1:
                addUrl = URLs.ALARM_BER;
                break;
            case 2:
                addUrl = URLs.ALARM_NPA;
                break;
            case 3:
                addUrl = URLs.ALARM_QAM;
                break;
            case 4:
                addUrl = URLs.ALARM_DCT;
                break;
        }
        String url = URLs.BASE_URL + addUrl;

        String result = null;
        try {
            Response response = OkHttpUtils
                    .post()
                    .url(url)
                    .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                    .addParams("pageIndex", String.valueOf(pageIndex))
                    .addParams("pageSize", PAGE_SIZE)
                    .addParams("alarm_level", String.valueOf(alertLevel))
                    .addParams("user_id", username)
                    .build()
                    .execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                result = ERROR;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}


