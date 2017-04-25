package binbin.leslie.cn.myvediolive.model;

import binbin.leslie.cn.myvediolive.callback.CommonCallback;

/**
 * Created by pc on 2017/3/17.
 */

public interface ILoginModel  extends IModel{
    void mRequestToLogin(String username, String password, CommonCallback callback);

}
