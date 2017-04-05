package leslie.binbin.cn.googleplay.ui.fragment;


import android.view.View;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.SubjectInfo;
import leslie.binbin.cn.googleplay.http.protocol.SubjectProtocol;
import leslie.binbin.cn.googleplay.ui.adapter.MyBaseAdapter;
import leslie.binbin.cn.googleplay.ui.holder.BaseHolder;
import leslie.binbin.cn.googleplay.ui.holder.SubjectHolder;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.ui.view.MyListView;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new SubjectAdapter(mData));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol protocol = new SubjectProtocol();
        mData = protocol.getData(0);
        return check(mData);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder() {

            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            ArrayList<SubjectInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }
    }
}
