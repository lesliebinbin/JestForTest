package binbin.leslie.cn.myvediolive.callback;

/**
 * Created by pc on 2017/3/18.
 */

public interface ContactHttpCallback<T> {
    void onSuccess(T t);

    void onFailed(int code, String errorMsg);
}
