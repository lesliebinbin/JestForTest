package leslie.binbin.cn.googleplay.ui.fragment;


import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.protocol.HomeProtocol;
import leslie.binbin.cn.googleplay.ui.activity.HomeDetailActivity;
import leslie.binbin.cn.googleplay.ui.adapter.MyBaseAdapter;
import leslie.binbin.cn.googleplay.ui.holder.BaseHolder;
import leslie.binbin.cn.googleplay.ui.holder.HomeHeaderHolder;
import leslie.binbin.cn.googleplay.ui.holder.HomeHolder;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.ui.view.MyListView;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class HomeFragment extends BaseFragment {

    private ArrayList<AppInfo> data;
    private ArrayList<String> mPictureList;

    //如果加载数据成功,就回掉此方法,在主线程运行
    @Override
    public View onCreateSuccessView() {
//        TextView view = new TextView(UIUtils.getContext());
//        view.setText(getClass().getSimpleName());
        MyListView view = new MyListView(UIUtils.getContext());


        //给listView添加头部展示轮播条
        HomeHeaderHolder header = new HomeHeaderHolder();
        view.addHeaderView(header.getRootView());

        if(mPictureList!=null){
            //设置轮播条数据
            header.setData(mPictureList);
        }

        view.setAdapter(new HomeAdapter(data));
        view.setOnItemClickListener((parent, view1, position, id) -> {

            AppInfo appInfo = data.get(position-1);//去掉头部数据

            if(appInfo!=null){
                Intent intent = new Intent(UIUtils.getContext(), HomeDetailActivity.class);
                intent.putExtra("packageName",appInfo.packageName);
                startActivity(intent);
            }

        });

        return view;
    }

    // 运行在子线程,可以直接执行耗时网络操作
    @Override
    public LoadingPage.ResultState onLoad() {
      //请求网络

        HomeProtocol protocol = new HomeProtocol();
        //加载第一页数据
        data = protocol.getData(0);

        mPictureList = protocol.getPictureList();
        return check(data);//校验数据并返回
    }



    class HomeAdapter extends MyBaseAdapter<AppInfo>{

        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
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
