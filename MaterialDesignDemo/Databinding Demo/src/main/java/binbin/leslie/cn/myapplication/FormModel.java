package binbin.leslie.cn.myapplication;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by pc on 2017/3/29.
 */

public class FormModel extends BaseObservable{
    private String mName;
    private String mPassword;

    public FormModel() {
    }

    public FormModel(String name, String password) {
        mName = name;
        mPassword = password;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(binbin.leslie.cn.myapplication.BR.name);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(binbin.leslie.cn.myapplication.BR.password);
    }
}
