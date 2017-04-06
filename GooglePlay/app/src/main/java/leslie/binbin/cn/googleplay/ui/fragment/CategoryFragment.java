package leslie.binbin.cn.googleplay.ui.fragment;


import android.view.View;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.domain.CategoryInfo;
import leslie.binbin.cn.googleplay.http.protocol.CategoryProtocol;
import leslie.binbin.cn.googleplay.ui.adapter.MyBaseAdapter;
import leslie.binbin.cn.googleplay.ui.holder.BaseHolder;
import leslie.binbin.cn.googleplay.ui.holder.CategoryHolder;
import leslie.binbin.cn.googleplay.ui.holder.TitleHolder;
import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.ui.view.MyListView;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mData;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new CategoryAdapter(mData));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        mData = protocol.getData(0);

        return check(mData);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{

        public CategoryAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder(int position) {
            //判断是标题类型还是普通分类类型,返回不同的Holder
            CategoryInfo info = mData.get(position);

            if(info.isTitle){
                return new TitleHolder();
            }else{
                return new CategoryHolder();
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;//在原来基础上增加一种标题类
        }

        @Override
        public int getInnerType(int position) {
            //判断是否是标题类型还是普通分类类型
            CategoryInfo info = mData.get(position);

            if(info.isTitle){
                //返回标题类型
                return super.getInnerType(position)+1;//原来类型基础上增加一种标题类型
                //注意,将原来Normal的类型修改为1
            }else{
                //返回普通类型
                return super.getInnerType(position);
            }
        }

        @Override
        public boolean hasMore() {
            return false;//没有更多数据,需要隐藏加载更多数据
        }

        @Override
        public ArrayList<CategoryInfo> onLoadMore() {
            return null;
        }
    }
}
