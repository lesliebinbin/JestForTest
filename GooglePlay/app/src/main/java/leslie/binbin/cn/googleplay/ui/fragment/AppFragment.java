package leslie.binbin.cn.googleplay.ui.fragment;


import android.view.View;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.protocol.AppProtocol;
import leslie.binbin.cn.googleplay.ui.adapter.MyBaseAdapter;
import leslie.binbin.cn.googleplay.ui.holder.AppHolder;
import leslie.binbin.cn.googleplay.ui.holder.BaseHolder;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.ui.view.MyListView;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> data;

    //只有成功才走此方法
    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new AppAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol protocol = new AppProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }
    }
}
