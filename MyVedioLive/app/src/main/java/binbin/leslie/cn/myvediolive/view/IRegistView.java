package binbin.leslie.cn.myvediolive.view;

/**
 * Created by pc on 2017/3/18.
 */

public interface IRegistView extends IView {
    void requestToRegist();

    void onSuccess();

    void onFailed(int code, String errorMsg);
}
