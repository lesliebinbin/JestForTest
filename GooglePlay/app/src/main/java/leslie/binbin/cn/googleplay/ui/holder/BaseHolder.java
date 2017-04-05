package leslie.binbin.cn.googleplay.ui.holder;

import android.view.View;

/**
 * Created by pc on 2017/4/4.
 */

public abstract class BaseHolder<T> {

    private final View mRootView;
    private T mData;

    //当new这个对象的时候就会加载布局,初始化控件,设置tag

    public BaseHolder(){
        mRootView = initView();
        mRootView.setTag(this);
    }

    //1.加载布局文件
    //2.初始化控件findViewById
    public abstract View initView();

    //返回item的布局对象
    public View getRootView(){
        return mRootView;
    }

    //设置当前item的数据
    public void setData(T data){
        mData = data;
        refreshView(mData);
    }

    //获取当前item的数据
    public T getData(){
        return mData;
    }

    public abstract void refreshView(T data);
}
