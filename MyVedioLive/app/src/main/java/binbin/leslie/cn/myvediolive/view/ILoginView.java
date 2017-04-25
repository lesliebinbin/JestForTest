package binbin.leslie.cn.myvediolive.view;

/**
 * Created by pc on 2017/3/17.
 */

public interface ILoginView extends IView{
    void onLoginSuccess();
    void onLoginFailed(String error);
}
