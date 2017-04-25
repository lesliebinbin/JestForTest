package binbin.leslie.cn.myvediolive.presenter.impl;

import binbin.leslie.cn.myvediolive.model.ILoginModel;
import binbin.leslie.cn.myvediolive.model.impl.LoginModelImpl;
import binbin.leslie.cn.myvediolive.presenter.ILoginPresenter;
import binbin.leslie.cn.myvediolive.callback.CommonCallback;
import binbin.leslie.cn.myvediolive.view.ILoginView;

/**
 * Created by pc on 2017/3/17.
 */

public class LoginPresenterImpl implements ILoginPresenter{

    private ILoginView mLoginView;
    private ILoginModel mLoginModel;

    public LoginPresenterImpl(ILoginView view){
        mLoginView = view;
        mLoginModel = new LoginModelImpl();
    }

    @Override
    public void pRequestToLogin(String username, String password) {
        mLoginModel.mRequestToLogin(username, password, new CommonCallback() {
            @Override
            public void onSuccess(Object object) {
                mLoginView.onLoginSuccess();
            }

            @Override
            public void onError(Object object) {
                String error = (String) object;
                mLoginView.onLoginFailed(error);
            }
        });
    }
}
