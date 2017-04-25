package binbin.leslie.cn.myvediolive.model.impl;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import binbin.leslie.cn.myvediolive.model.ILoginModel;
import binbin.leslie.cn.myvediolive.callback.CommonCallback;

/**
 * Created by pc on 2017/3/17.
 */

public class LoginModelImpl implements ILoginModel {
    @Override
    public void mRequestToLogin(String username, String password,final CommonCallback callback) {
        LoginInfo info = new LoginInfo(username,password); // config...
        RequestCallback<LoginInfo> rCallback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                callback.onSuccess(loginInfo);
            }

            @Override
            public void onFailed(int i) {
                callback.onError(String.valueOf(i));
            }

            @Override
            public void onException(Throwable throwable) {

            }
        };

        NIMClient.getService(AuthService.class).login(info).setCallback(rCallback);
    }
}
