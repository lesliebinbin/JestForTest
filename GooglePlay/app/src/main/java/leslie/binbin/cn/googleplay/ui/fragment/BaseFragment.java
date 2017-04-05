package leslie.binbin.cn.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * Created by pc on 2017/4/2.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerBundle,Bundle savedInstanceState) {
        //使用TextView显示当前的类名

        mLoadingPage = new LoadingPage(UIUtils.getContext()){

            @Override
            public View onCreateSuccessView() {
                //注意:此处一定要加上BaseFragment.this,否则递归了,直接栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public LoadingPage.ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        return mLoadingPage;
    }

    //加载成功的布局,必须由子类来实现
    public abstract View onCreateSuccessView();

    //加载网络数据,必须由子类来实现
    public abstract LoadingPage.ResultState onLoad();

    //开始加载数据的方法
    public void loadData(){
        if(mLoadingPage!=null) {
            mLoadingPage.loadData();
        }
    }

    //对网络返回数据的合法性进行校验
    public LoadingPage.ResultState check(Object obj){
        if(obj!=null){
            if(obj instanceof ArrayList){//判断是否是集合
                ArrayList list = (ArrayList) obj;
                if(list.isEmpty()){
                    return LoadingPage.ResultState.STATE_EMPTY;
                }else{
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }

        return LoadingPage.ResultState.STATE_ERROR;
    }
}
