package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lemon.c.nms.R;
import com.lemon.c.nms.adapter.SpectrumAdapter;
import com.lemon.c.nms.bean.DeviceRoomBean;
import com.lemon.c.nms.bean.SpectrumBean;
import com.lemon.c.nms.network.NetWork;
import com.lemon.c.nms.network.URLs;
import com.lemon.c.nms.utils.SpecComparator;
import com.melnykov.fab.FloatingActionButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

public class SpectrumActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener{
    private static final String orderType = "2" ;

    private ListView spectrum_list;
    FloatingActionButton datePicker;

    private SpectrumAdapter adapter;
    private List<SpectrumBean.DataBean> spectrumList = new ArrayList<>();
    private String startDate,endDate;
    private int sr_id = 0;
    private int pageIndex = 0;

    private TextView tv_room;
    private List<String> roomList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();

    private View footer;
    private TextView tv_more;
    private LinearLayout loading;
    private int visibleLastIndex = 0;
    private int visibleItemCount;

    private SwipeRefreshLayout swipe_spectrum;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.spectrum_fab:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        SpectrumActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.tv_room:
                new MaterialDialog.Builder(this)
                        .items(roomList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                tv_room.setText(text);
                                sr_id = idList.get(which);
                                pageIndex = 0;
                                tv_more.setText(R.string.load_more);
                                adapter = null;
                                spectrumList.clear();
                                getData();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_spectrum;
    }

    @Override
    protected void initParams(Bundle bundle) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate = dateFormat.format(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
        endDate = dateFormat.format(System.currentTimeMillis()+ 1000 * 60 * 60 * 24);
    }

    @Override
    public void initView(View view) {
        Toolbar toolbar = $(R.id.special_toolbar);
        toolbar.setTitle("频谱工单");
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        tv_room = $(R.id.tv_room);
        tv_room.setText("全部机房");
        tv_room.setOnClickListener(this);


        spectrum_list = $(R.id.spectrum_list);
        datePicker = $(R.id.spectrum_fab);
        datePicker.attachToListView(spectrum_list);



        //点击加载更多
        footer = getLayoutInflater().inflate(R.layout.footer, null);
        spectrum_list.addFooterView(footer);
        tv_more = (TextView) footer.findViewById(R.id.text_more);
        tv_more.setText(R.string.load_more);
        loading = (LinearLayout) footer.findViewById(R.id.load_more);
        swipe_spectrum = $(R.id.spectrum_contain);
    }

    @Override
    public void setListener() {
        datePicker.setOnClickListener(this);

        spectrum_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int itemsLastIndex = adapter.getCount() - 1;  //数据集最后一项的索引
                int lastIndex = itemsLastIndex + 1;
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && visibleLastIndex == lastIndex) {
                    if(tv_more.getText().equals("已加载全部")) return;
                    tv_more.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    getData();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                visibleItemCount = i1;
                visibleLastIndex = i + i1 - 1;

            }
        });

        swipe_spectrum.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                getData();
                swipe_spectrum.setRefreshing(false);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        getPortData();
        getData();
    }

    public void getData(){
        String id;
        if(sr_id == 0){
            id = "";
        } else{
            id = String.valueOf(sr_id);
        }
        String url = URLs.BASE_URL + URLs.SPECTRUM_WORKORDER;
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .addParams("sr_id",id)
                .addParams("order_type",orderType)
                .addParams("date1",startDate)
                .addParams("date2",endDate)
                .addParams("pageIndex",String.valueOf(pageIndex))
                .addParams("pageSize", NetWork.PAGE_SIZE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据失败"+ e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        SpectrumBean spectrum1 = gson.fromJson(response, SpectrumBean.class);
                        List<SpectrumBean.DataBean> spectrum = spectrum1.getData();
                        if(spectrum.size() < Integer.parseInt(NetWork.PAGE_SIZE)){
                            tv_more.setText(R.string.no_more);
                        }
                        for(SpectrumBean.DataBean s :spectrum){
                            spectrumList.add(s);
                        }
                        if(adapter == null){
                            adapter = new SpectrumAdapter(SpectrumActivity.this,spectrumList);
                            spectrum_list.setAdapter(adapter);
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                        pageIndex += 1;
                        tv_more.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    public void getPortData(){
        roomList.add("全部机房");
        idList.add(0);
        String url = URLs.BASE_URL + URLs.ROOM_LIST;
        OkHttpUtils
                .get()
                .url(url)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<DeviceRoomBean> roomBeanList = new Gson().fromJson(response, new TypeToken<ArrayList<DeviceRoomBean>>() {
                        }.getType());
                        for (DeviceRoomBean room : roomBeanList) {
                            roomList.add(room.getText());
                            idList.add(room.getId());
                        }
                        tv_room.setText(roomList.get(0));
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spectrum_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SpecComparator c;
        switch (item.getItemId()){
            case R.id.spectrum_search:

                break;
                case R.id.sort_time:
                    c = new SpecComparator("getTime");
                    Collections.sort(spectrumList, Collections.reverseOrder(c));
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.sort_event:
                    c = new SpecComparator("getAlarm_name");
                    Collections.sort(spectrumList, Collections.reverseOrder(c));
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.sort_room:
                    c = new SpecComparator("getDepart_name");
                    Collections.sort(spectrumList, Collections.reverseOrder(c));
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.sort_sign:
                    c = new SpecComparator("getFixed");
                    Collections.sort(spectrumList, Collections.reverseOrder(c));
                    adapter.notifyDataSetChanged();
                    break;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String month = String.format("%02d", ++monthOfYear);
        String day = String.format("%02d",dayOfMonth);
        String month1 = String.format("%02d",++monthOfYearEnd);
        String day1 = String.format("%02d",dayOfMonthEnd);
        startDate = year +"-"+ month +"-"+ day;
        endDate = yearEnd +"_"+month1 +"-"+day1;
        spectrumList.clear();
        adapter = null;
        pageIndex = 0;
        tv_more.setText(R.string.load_more);
        getData();
    }
}
