package firstdemo.as.tedu.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.listView)
    ListView mListView;

    private ArrayList<String> mList = new ArrayList<>();
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListView();
    }

    private void initListView() {
        //准备数据
        for(int i=0;i<30;i++){
            mList.add("name - "+i);
        }
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    //如果垂直滑动,则需要关闭已经打开的layout
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = View.inflate(MainActivity.this,R.layout.adapter_list,null);
            }
            ViewHolder holder = ViewHolder.getHolder(convertView);

            holder.tv_name.setText(getItem(position));
            holder.swipeLayout.setTag(position);
            holder.swipeLayout.setOnSwipeStateChangedListener(new SwipeLayout.OnSwipeStateChangedListener() {
                @Override
                public void onOpen(Object tag) {
                    Toast.makeText(MainActivity.this, "第"+(Integer)tag+"个打开了", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClosed(Object tag) {
                    Toast.makeText(MainActivity.this, "第"+(Integer)tag+"个关闭了", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

    }

    static class ViewHolder{
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_delete)
        TextView tv_delete;
        @Bind(R.id.swipeLayout)
        SwipeLayout swipeLayout;

        public ViewHolder(View convertView){
            ButterKnife.bind(this,convertView);
        }


        public static ViewHolder getHolder(View convertView){
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if(holder==null){
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
