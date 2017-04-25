package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lemon.c.nms.R;
import com.lemon.c.nms.bean.DsNameBean;
import com.lemon.c.nms.bean.DsPortBean;
import com.lemon.c.nms.bean.DsTypeBean;
import com.lemon.c.nms.bean.SpectrumRoomBean;
import com.lemon.c.nms.network.URLs;
import com.lemon.c.nms.utils.DialogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class TimeSpectrumActivity extends BaseActivity {

    private TextView room_name,ds_name,ds_type,ds_port;
    private Button btn_submit;

    private List<String> roomList = new ArrayList<>();
    private List<String> srIdList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private List<String> equIdList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private List<String> rpmIdList = new ArrayList<>();
    private List<String> portList = new ArrayList<>();
    private List<String> portIdList = new ArrayList<>();

    private String sr_id,equ_id,rpm_id,port_id;

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.room_name:
                new MaterialDialog.Builder(this)
                        .items(roomList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                room_name.setText(text);
                                sr_id = srIdList.get(which);
                                getDsName();
                            }
                        })
                        .show();
                break;
            case R.id.DS_name:
                if(ds_name.getText().equals("暂无数据")){
                    DialogUtils.showAlert(this,"暂无该地区数据");
                    return;
                }
                new MaterialDialog.Builder(this)
                        .items(nameList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                ds_name.setText(text);
                                equ_id = equIdList.get(which);
                                getDsType();
                            }
                        })
                        .show();
                break;
            case R.id.ds_type:
                if(ds_type.getText().equals("暂无数据")){
                    DialogUtils.showAlert(this,"暂无该地区数据");
                    return;
                }
                new MaterialDialog.Builder(this)
                        .items(typeList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                ds_type.setText(text);
                                rpm_id = equIdList.get(which);
                            }
                        })
                        .show();
                break;
            case R.id.ds_port:
                if(ds_port.getText().equals("暂无数据")){
                    DialogUtils.showAlert(this,"暂无该地区数据");
                    return;
                }
                new MaterialDialog.Builder(this)
                        .items(portList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                ds_port.setText(text);
                                port_id = portIdList.get(which);
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_time_spectrum;
    }

    @Override
    protected void initParams(Bundle bundle) {
        setToolbarTitle("实时频谱");
    }

    @Override
    public void initView(View view) {
        room_name = $(R.id.room_name);
        ds_name = $(R.id.DS_name);
        ds_type = $(R.id.ds_type);
        ds_port = $(R.id.ds_port);
        btn_submit = $(R.id.DS_ensure);

    }

    @Override
    public void setListener() {
        room_name.setOnClickListener(this);
        ds_name.setOnClickListener(this);
        ds_type.setOnClickListener(this);
        ds_port.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        String url = URLs.BASE_URL + URLs.SPECTRUM_ROOM_URL;
        OkHttpUtils
                .get()
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        List<SpectrumRoomBean> spectrumRoom = gson.fromJson(response,new TypeToken<ArrayList<SpectrumRoomBean>>(){}.getType());
                        for(SpectrumRoomBean spec : spectrumRoom){
                            roomList.add(spec.getName());
                            srIdList.add(spec.getId());
                        }
                        room_name.setText(roomList.get(1));
                        sr_id = srIdList.get(1);
                        getDsName();
                    }
                });
    }

    public void getDsName(){
        nameList.clear();
        equIdList.clear();
        String url = URLs.BASE_URL + URLs.SPECTRUM_ROOM_URL + "?id="+ sr_id ;
        OkHttpUtils
                .get()
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if(response.equals("[]")){
                            ds_name.setText("暂无数据");
                            ds_type.setText("暂无数据");
                            ds_port.setText("暂无数据");
                            return;
                        }
                        Gson gson = new Gson();
                        List<DsNameBean> dsName = gson.fromJson(response,new TypeToken<ArrayList<DsNameBean>>(){}.getType());
                        for(DsNameBean dsn : dsName){
                            nameList.add(dsn.getName());
                            equIdList.add(dsn.getId());
                        }
                        ds_name.setText(nameList.get(0));
                        equ_id = equIdList.get(0);
                        getDsType();
                    }
                });
    }

    public void getDsType(){
        typeList.clear();
        rpmIdList.clear();
        String url = URLs.BASE_URL + URLs.SPECTRUM_ROOM_URL + "?id="+ equ_id +"&sr_id"+ sr_id;
        OkHttpUtils
                .get()
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        List<DsTypeBean> dsType = gson.fromJson(response,new TypeToken<ArrayList<DsTypeBean>>(){}.getType());
                        for(DsTypeBean type : dsType){
                            typeList.add(type.getName());
                            rpmIdList.add(type.getId());
                        }
                        ds_type.setText(typeList.get(0));
                        rpm_id = rpmIdList.get(0);
                        getPort();
                    }
                });
    }

    public void getPort(){
        String url = URLs.BASE_URL + URLs.SPECTRUM_ROOM_URL + "?id="+ rpm_id +"&sr_id"+ sr_id+"&equ_id" + equ_id;
        OkHttpUtils
                .get()
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        List<DsPortBean> dsPort = gson.fromJson(response,new TypeToken<ArrayList<DsPortBean>>(){}.getType());
                        for(DsPortBean port : dsPort){
                            portList.add(port.getName());
                            portIdList.add(port.getId());
                        }
                        ds_port.setText(portList.get(0));
                        port_id = portList.get(0);
                    }
                });
    }

}
