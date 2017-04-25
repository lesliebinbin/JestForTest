package binbin.leslie.cn.myvediolive.model.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.common.http.NimHttpClient;
import com.netease.nim.uikit.common.util.log.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import binbin.leslie.cn.myvediolive.callback.ContactHttpCallback;
import binbin.leslie.cn.myvediolive.constant.Constant;
import binbin.leslie.cn.myvediolive.model.IRegistModel;
import binbin.leslie.cn.myvediolive.util.CheckSumBuilderUtil;

import static android.content.ContentValues.TAG;
import static binbin.leslie.cn.myvediolive.constant.Constant.API_SERVER;
import static binbin.leslie.cn.myvediolive.constant.Constant.HEADER_CONTENT_TYPE;
import static binbin.leslie.cn.myvediolive.constant.Constant.HEADER_KEY_APP_CHECKSUM;
import static binbin.leslie.cn.myvediolive.constant.Constant.HEADER_KEY_APP_CURTIME;
import static binbin.leslie.cn.myvediolive.constant.Constant.HEADER_KEY_APP_KEY;
import static binbin.leslie.cn.myvediolive.constant.Constant.HEADER_KEY_APP_NONCE;
import static binbin.leslie.cn.myvediolive.constant.Constant.REQUEST_NICK_NAME;
import static binbin.leslie.cn.myvediolive.constant.Constant.REQUEST_PASSWORD;
import static binbin.leslie.cn.myvediolive.constant.Constant.REQUEST_USER_ACCID;
import static binbin.leslie.cn.myvediolive.constant.Constant.REQUEST_USER_NAME;
import static binbin.leslie.cn.myvediolive.constant.Constant.RESULT_CODE_SUCCESS;
import static binbin.leslie.cn.myvediolive.constant.Constant.RESULT_KEY_CODE;
import static binbin.leslie.cn.myvediolive.constant.Constant.RESULT_KEY_ERROR_MSG;

/**
 * Created by pc on 2017/3/18.
 */

public class RegistModelImpl implements IRegistModel {
    @Override
    public void mRequestToRegist(String username, String nickname, String password,final ContactHttpCallback<Void> callback) {
        String url = API_SERVER ;

        Map<String,String> headers = new HashMap<>();
        String appKey = Constant.VIDEO_KEY;
        String nonce = String.valueOf(new Random().nextInt(1000));
        String curTime = String.valueOf(System.currentTimeMillis());
        String checkSum = CheckSumBuilderUtil.getCheckSum(Constant.VIDEO_SECRET,nonce,curTime);
        try {
            nickname = URLEncoder.encode(nickname, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.put(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
        headers.put(HEADER_KEY_APP_KEY,appKey);
        headers.put(HEADER_KEY_APP_NONCE,nonce);
        headers.put(HEADER_KEY_APP_CURTIME,curTime);
        headers.put(HEADER_KEY_APP_CHECKSUM,checkSum);
        StringBuilder body = new StringBuilder();
        body.append(REQUEST_USER_ACCID).append("=").append(String.valueOf(System.currentTimeMillis())).append("&")
                .append(REQUEST_USER_NAME).append("=").append(username).append("&")
                .append(REQUEST_NICK_NAME).append("=").append(nickname).append("&")
                .append(REQUEST_PASSWORD).append("=").append(password);

        NimHttpClient.getInstance().execute(url, headers, body.toString(), ( response, code, exception) -> {
            if (code != 200 || exception != null) {
                LogUtil.e(TAG, "register failed : code = " + code + ", errorMsg = "
                        + (exception != null ? exception.getMessage() : "null"));
                if (callback != null) {
                    callback.onFailed(code, exception != null ? exception.getMessage() : "null");
                }
                System.out.println("进到ABC");
                return;
            }

            try {
                JSONObject resObj = JSONObject.parseObject(response);
                int resCode = resObj.getIntValue(RESULT_KEY_CODE);
                if (resCode == RESULT_CODE_SUCCESS) {
                    callback.onSuccess(null);
                } else {
                    String error = resObj.getString(RESULT_KEY_ERROR_MSG);
                    callback.onFailed(resCode, error);
                }
            } catch (JSONException e) {
                callback.onFailed(-1, e.getMessage());
            }
        });
    }
}
