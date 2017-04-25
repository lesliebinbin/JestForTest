package binbin.leslie.cn.myapplication;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

/**
 * Created by pc on 2017/3/28.
 */

public class Employee extends BaseObservable{

    private String mFirstName;
    private String mLastName;
    private ObservableBoolean mIsFired = new ObservableBoolean();
    private String mAvatar;

    public void setAvatar(String avatar){
        mAvatar = avatar;
    }

    public String getAvatar(){
        return mAvatar;
    }

    public Employee(String firstName,String lastName,boolean isFired){
        mFirstName = firstName;
        mLastName = lastName;
        mIsFired.set(isFired);
    }

    @Bindable
    public String getFirstName(){
        return mFirstName;
    }

    @Bindable
    public String getLastName(){
        return mLastName;
    }

    public void setFirstName(String firstName){
        mFirstName = firstName;
        notifyPropertyChanged(binbin.leslie.cn.myapplication.BR.firstName);
    }

    public void setLastName(String lastName){
        mLastName = lastName;
        notifyPropertyChanged(binbin.leslie.cn.myapplication.BR.lastName);
    }

    public void setIsFired(boolean fired){
        mIsFired.set(fired);
        notifyChange();
    }

    public ObservableBoolean getIsFired(){
        return mIsFired;
    }


}
