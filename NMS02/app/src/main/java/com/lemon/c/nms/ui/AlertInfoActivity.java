package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lemon.c.nms.R;
import com.lemon.c.nms.adapter.AlertInfoAdapter;
import com.lemon.c.nms.bean.AlertInfoBean;
import com.lemon.c.nms.network.NetWork;
import com.lemon.c.nms.utils.AlertInfoParse;
import com.lemon.c.nms.utils.MyComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlertInfoActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private String title;

    private int alertType, alertLevel, pageIndex;
    private RelativeLayout loading_anim;

    private SwipeRefreshLayout swipe;
    private View footer;
    private TextView tv_more;
    private LinearLayout loading;
    private int visibleLastIndex = 0;
    private int visibleItemCount;

    //菜单栏
    private LinearLayout ll_type;
    private TextView menu_name,menu_port,menu_type;
    private ImageView icon_order;
    private int order;

    AlertInfoAdapter mAdapter;

    List<AlertInfoBean> alertinfoList = new ArrayList<>();

    private ListView main_list;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(loading_anim.getVisibility() == View.VISIBLE){
                        loading_anim.setVisibility(View.GONE);
                    }
                    if(swipe.isRefreshing()){
                        swipe.setRefreshing(false);
                    } else {
                        tv_more.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                    if(mAdapter == null){
                        mAdapter = new AlertInfoAdapter(AlertInfoActivity.this, alertinfoList);
                        main_list.setAdapter(mAdapter);
                    }
                    mAdapter.notifyDataSetChanged();
                    pageIndex += 1;
                    break;
                case 2:
                    mAdapter.notifyDataSetChanged();
                    tv_more.setText(R.string.no_more);
                    tv_more.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    break;
                case 3:
                    showToast("数据解析异常");
                case -1:
                    loading_anim.setVisibility(View.GONE);
                    showToast("获取数据失败");
                    break;
            }
        }
    };

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.ll_order :
                if(alertType != 3) {
                    if (order == 0) {
                        icon_order.setImageResource(R.drawable.paixu_down);
                        order = 1;
                    } else {
                        icon_order.setImageResource(R.drawable.paixu_up);
                        order = 0;
                    }
                    Collections.sort(alertinfoList, new MyComparator(order));
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_alert_info;
    }

    @Override
    protected void initParams(Bundle bundle) {
        setToolbarTitle("告警信息表");
        alertType = 0;
        alertLevel = 1;
        pageIndex = 0;

        order = 0;
    }

    @Override
    public void initView(View view) {
        loading_anim = $(R.id.loading_anim);
        main_list = $(R.id.alert_lv);

        //点击加载更多
        footer = getLayoutInflater().inflate(R.layout.footer, null);
        main_list.addFooterView(footer);
        tv_more = (TextView) footer.findViewById(R.id.text_more);
        tv_more.setText(R.string.load_more);
        loading = (LinearLayout) footer.findViewById(R.id.load_more);
        swipe = $(R.id.swipe_container);

        //菜单
        menu_name = $(R.id.menu_name);
        menu_port = $(R.id.menu_port);
        menu_type = $(R.id.menu_type);
        icon_order = $(R.id.paixu_icon);
        ll_type = $(R.id.ll_order);
        ll_type.setOnClickListener(this);
    }

    @Override
    public void setListener() {
        swipe.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorAccent));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                alertinfoList.clear();
                pageIndex = 0;
                tv_more.setText(R.string.load_more);
                loadData();
            }
        });

        main_list.setOnScrollListener(this);
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void doBusiness(final Context mContext) {
        loadData();
    }

    public void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    String result = NetWork.getAlertData(AlertInfoActivity.this, alertType, alertLevel, pageIndex);
                    Message msg = new Message();
                    if (result == NetWork.ERROR) {
                        msg.what = -1;
                    } else {
                        List<AlertInfoBean> finalList = AlertInfoParse.doGson(result,alertType);

                        for(AlertInfoBean alertInfo : finalList){
                            alertinfoList.add(alertInfo);
                        }if(alertType != 3) {
                            Collections.sort(alertinfoList, new MyComparator(order));
                        }
                        if(finalList.size() == 0){
                            msg.what = 3;
                        }
                        if(finalList.size() < Integer.parseInt(NetWork.PAGE_SIZE) && finalList.size() != 0){
                            msg.what = 2;
                        } else {
                            msg.what = 1;
                        }
                    }
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alert_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        pageIndex = 0;
        alertinfoList.clear();
        tv_more.setText(R.string.load_more);
        switch (item.getItemId()) {
            case R.id.snr_low:
                alertType = 0;
                menu_type.setText("SNR值");
                break;
            case R.id.ber_high:
                alertType = 1;
                menu_type.setText("BER值");
                break;
            case R.id.npa_low:
                alertType = 2;
                menu_type.setText("NPA值");
                break;
            case R.id.port_change:
                menu_type.setText("调制");
                icon_order.setVisibility(View.GONE);
                alertType = 3;
                break;
            case R.id.dct_offline:
                alertType = 4;
                showToast("暂无该数据");
                break;
            case R.id.alert_level1:
                alertLevel = 1;
                break;
            case R.id.alert_level2:
                alertLevel = 2;
                break;
            case R.id.alert_level3:
                alertLevel = 3;
                break;
            case R.id.alert_level4:
                alertLevel = 4;
                break;
        }
        loadData();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        int itemsLastIndex = mAdapter.getCount() - 1;  //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;
        if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && visibleLastIndex == lastIndex) {
            if(tv_more.getText().equals("已加载全部")) return;
                tv_more.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                loadData();
            }
        }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.visibleItemCount = i1;
        visibleLastIndex = i + i1 - 1;

    }

}
