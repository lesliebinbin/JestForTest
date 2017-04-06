package leslie.binbin.cn.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.ui.holder.BaseHolder;
import leslie.binbin.cn.googleplay.ui.holder.MoreHolder;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 *对adapter的封装
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    //此处必须从0开始写
    private static final int TYPE_NORMAL = 1;//正常类型
    private static final int TYPE_MORE = 0;//更多类型

    private ArrayList<T> mData;


    public MyBaseAdapter(ArrayList<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }//增加加载更多布局的数量

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    //返回布局类型的个数
    @Override
    public int getViewTypeCount() {
        return 2;//返回两种类型,普通布局+加载更多布局
    }

    //返回的是当前位置应展示的布局类型
    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? TYPE_MORE : getInnerType(position);
    }

    //子类可以重写此方法来更改返回的布局类型
    public int getInnerType(int position) {
        return TYPE_NORMAL;//默认就是普通的类型
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            //1.加载布局文件
            //2.初始化控件findViewById
            //3.打一个标记tag
            if (getItemViewType(position) == TYPE_MORE) {
                //加载更多类型
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder(position);//子类返回具体对象
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        //4.根据数据来刷新页面
        if (getItemViewType(position) != TYPE_MORE) {
            holder.setData(getItem(position));
        } else {
            //加载更多布局
            //一旦加载更多布局显示出来,就开始加载更多
            //只有在有更多数据的状态下
            MoreHolder moreHolder = (MoreHolder) holder;
            if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
                loadMore(moreHolder);
            }
        }


        return holder.getRootView();
    }

    //子类可以重写此方法来决定是否可以加载更多
    public boolean hasMore() {
        return true;
    }

    //返回当前页面的holder对象,必须子类实现
    public abstract BaseHolder<T> getHolder(int position);

    private boolean isLoadMore = false;//是否正在加载更多

    //加载更多数据
    public void loadMore(final MoreHolder holder) {
        if (!isLoadMore) {
            isLoadMore = true;
            new Thread() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                if (moreData.size() < 20) {
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    //还有更多数据
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }

                                //将更多数据追加到集合中
                                mData.addAll(moreData);
                                //刷新页面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            }.start();
        }
    }
    //加载更多数据,必须由子类实现
    public abstract ArrayList<T> onLoadMore ();

    //获取当前集合的大小
    public int getListSize(){
        return mData.size();
    }
}
