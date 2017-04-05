package leslie.binbin.cn.googleplay.ui.fragment;


import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.protocol.HomeProtocol;
import leslie.binbin.cn.googleplay.ui.adapter.MyBaseAdapter;
import leslie.binbin.cn.googleplay.ui.holder.BaseHolder;
import leslie.binbin.cn.googleplay.ui.holder.HomeHolder;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class HomeFragment extends BaseFragment {

    private ArrayList<AppInfo> data;

    //如果加载数据成功,就回掉此方法,在主线程运行
    @Override
    public View onCreateSuccessView() {
//        TextView view = new TextView(UIUtils.getContext());
//        view.setText(getClass().getSimpleName());
        ListView view = new ListView(UIUtils.getContext());
        view.setAdapter(new HomeAdapter(data));
        return view;
    }

    //运行在子线程,可以直接执行网络操作
    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络

        HomeProtocol protocol = new HomeProtocol();
        //加载第一页数据
        data = protocol.getData(0);

        return check(data);//校验数据并返回
    }



    class HomeAdapter extends MyBaseAdapter<AppInfo>{

        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder() {
            return new HomeHolder();
        }

        //此方法在子线程被调用
        @Override
        public ArrayList<AppInfo> onLoadMore() {
            HomeProtocol protocol = new HomeProtocol();
            //下一页的数据index就等于当前集合的大小
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }


    }

}
