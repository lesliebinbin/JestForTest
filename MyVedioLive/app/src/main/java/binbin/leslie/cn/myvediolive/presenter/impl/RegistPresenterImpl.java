package binbin.leslie.cn.myvediolive.presenter.impl;

import binbin.leslie.cn.myvediolive.callback.CommonCallback;
import binbin.leslie.cn.myvediolive.callback.ContactHttpCallback;
import binbin.leslie.cn.myvediolive.model.ILoginModel;
import binbin.leslie.cn.myvediolive.model.IRegistModel;
import binbin.leslie.cn.myvediolive.model.impl.LoginModelImpl;
import binbin.leslie.cn.myvediolive.model.impl.RegistModelImpl;
import binbin.leslie.cn.myvediolive.presenter.IRegistPresenter;
import binbin.leslie.cn.myvediolive.view.IRegistView;

/**
 * Created by pc on 2017/3/18.
 */

public class RegistPresenterImpl implements IRegistPresenter{
    private IRegistView mRegistView;
    private IRegistModel mRegistModel;

    public RegistPresenterImpl(IRegistView view){
        mRegistView = view;
        mRegistModel = new RegistModelImpl();
    }

    @Override
    public void pRequestTORegist(String username, String nickname, String password) {
        mRegistModel.mRequestToRegist(username, nickname, password, new ContactHttpCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ILoginModel model = new LoginModelImpl();

                model.mRequestToLogin(username, password, new CommonCallback() {
                    @Override
                    public void onSuccess(Object objcet) {
                        mRegistView.onSuccess();
                    }

                    @Override
                    public void onError(Object object) {

                    }
                });
            }

            @Override
            public void onFailed(int code, String errorMsg) {
                mRegistView.onFailed(code,errorMsg);
            }
        });
    }
}
