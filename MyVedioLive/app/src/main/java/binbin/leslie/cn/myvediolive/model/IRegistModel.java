package binbin.leslie.cn.myvediolive.model;

import binbin.leslie.cn.myvediolive.callback.ContactHttpCallback;

/**
 * Created by pc on 2017/3/18.
 */

public interface IRegistModel {
    void mRequestToRegist(String username, String nickname,
                          String password, ContactHttpCallback<Void> callback);
}
