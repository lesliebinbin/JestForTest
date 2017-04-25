package com.lemon.c.nms.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lemon.c.nms.R;
import com.lemon.c.nms.bean.CMTSBean;
import com.lemon.c.nms.bean.DeviceRoomBean;
import com.lemon.c.nms.bean.PortBean;
import com.lemon.c.nms.network.URLs;
import com.lemon.c.nms.utils.DialogUtils;
import com.lemon.c.nms.utils.JsonUtils;
import com.lemon.c.nms.utils.TimeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class CMTSInfoActivity extends BaseActivity {

    private TextView device_name, CMTS_name, port_type, port_name;
    private Button btn_submit;

    private List<String> roomList = new ArrayList<>();
    private List<Integer> idList = new ArrayList<>();
    private List<String> CMTSNameList = new ArrayList<>();
    private List<Integer> CMTSIdList = new ArrayList<>();
    private List<String> portNameList = new ArrayList<>();
    private List<Integer> portIdList = new ArrayList<>();

    private String[] portTypeArr = {"上行端口", "下行端口"};

    //上传端口详细信息
    private CardView upPortInfo, downPortInfo;
    private TextView portInfo1, portInfo2, portInfo3, portInfo4, portInfo5,
            portInfo6, portInfo7, portInfo8, portInfo9, portInfo10, portInfo11, portInfo12;
    private TextView downPortInfo1, downPortInfo2, downPortInfo3, downPortInfo4, downPortInfo5;
    //for portData
    private int CMTSId;
    private int portType;
    private int portId = 2386;
    //chart类型
    private int chartType;
    //表格

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.device_name:
                new MaterialDialog.Builder(this)
                        .items(roomList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                device_name.setText(text);
                                getCMTSData(idList.get(which));
                            }
                        })
                        .show();
                break;
            case R.id.CMTS_name:
                if(CMTS_name.getText().equals("暂无数据")){
                    DialogUtils.showAlert(this,"暂无该地区数据");
                    return;
                }
                new MaterialDialog.Builder(this)
                        .items(CMTSNameList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                CMTS_name.setText(text);
                                CMTSId = CMTSIdList.get(which);
                                getPortData(CMTSId, portType);
                            }
                        })
                        .show();
                break;
            case R.id.PORT_type:
                if(port_type.getText().equals("暂无数据")){
                    DialogUtils.showAlert(this,"暂无该地区数据");
                    return;
                }
                new MaterialDialog.Builder(this)
                        .title("选择端口类型")
                        .items(portTypeArr)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                port_type.setText(text);
                                portType = which;
                                getPortData(CMTSId, portType);
                            }
                        })
                        .positiveText("取消")
                        .show();
                break;
            case R.id.PORT_name:
                if(port_name.getText().equals("暂无数据")){
                    DialogUtils.showAlert(this,"暂无该地区数据");
                    return;
                }
                new MaterialDialog.Builder(this)
                        .items(portNameList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                port_name.setText(text);
                                portId = portIdList.get(which);
                            }
                        })
                        .show();
                break;
            case R.id.CMTS_ensure:
                if (portType == 0) {
                    if (upPortInfo.getVisibility() == View.GONE) {
                        upPortInfo.setVisibility(View.VISIBLE);
                        downPortInfo.setVisibility(View.GONE);
                    }
                } else {
                    if (downPortInfo.getVisibility() == View.GONE) {
                        downPortInfo.setVisibility(View.VISIBLE);
                        upPortInfo.setVisibility(View.GONE);
                    }
                }
                getPortDetail(portId, portType);
                getChartDetail(portId,1);
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_cmtsinfo;
    }

    @Override
    protected void initParams(Bundle bundle) {
        setToolbarTitle("端口信息");
    }

    @Override
    public void initView(View view) {
        device_name = $(R.id.device_name);
        CMTS_name = $(R.id.CMTS_name);
        port_type = $(R.id.PORT_type);
        port_name = $(R.id.PORT_name);
        btn_submit = $(R.id.CMTS_ensure);

        device_name.setOnClickListener(this);
        CMTS_name.setOnClickListener(this);
        port_type.setOnClickListener(this);
        port_name.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        port_type.setText("上行端口");

        //端口详细信息
        upPortInfo = $(R.id.up_port_info);
        portInfo1 = $(R.id.port_info1);
        portInfo2 = $(R.id.port_info2);
        portInfo3 = $(R.id.port_info3);
        portInfo4 = $(R.id.port_info4);
        portInfo5 = $(R.id.port_info5);
        portInfo6 = $(R.id.port_info6);
        portInfo7 = $(R.id.port_info7);
        portInfo8 = $(R.id.port_info8);
        portInfo9 = $(R.id.port_info9);
        portInfo10 = $(R.id.port_info10);
        portInfo11 = $(R.id.port_info11);
        portInfo12 = $(R.id.port_info12);

        downPortInfo = $(R.id.down_port_info);
        downPortInfo1 = $(R.id.down_port_info1);
        downPortInfo2 = $(R.id.down_port_info2);
        downPortInfo3 = $(R.id.down_port_info3);
        downPortInfo4 = $(R.id.down_port_info4);
        downPortInfo5 = $(R.id.down_port_info5);

        //chart
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(final Context mContext) {
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
                        device_name.setText(roomList.get(0));
                        getCMTSData(idList.get(0));
                    }
                });
    }

    public void getCMTSData(int id) {
        final String url = URLs.BASE_URL + URLs.CMTS_LIST + String.valueOf(id);
        OkHttpUtils
                .post()
                .tag(this)
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
                            CMTS_name.setText(R.string.no_data);
                            port_type.setText(R.string.no_data);
                            port_name.setText(R.string.no_data);
                            return;
                        }
                        CMTSNameList.clear();
                        CMTSIdList.clear();
                        List<CMTSBean> CMTSList = new Gson().fromJson(response, new TypeToken<ArrayList<CMTSBean>>() {}.getType());
                        for (CMTSBean cmts : CMTSList) {
                            CMTSNameList.add(cmts.getText());
                            CMTSIdList.add(cmts.getId());
                        }
                        CMTSId = CMTSIdList.get(0);
                        CMTS_name.setText(CMTSNameList.get(0));
                        port_type.setText("上行端口");
                        getPortData(CMTSId, 0);
                    }
                });
    }

    public void getPortData(int id, int type) {
        String url;
        if (type == 0) {
            url = URLs.BASE_URL + URLs.UPPORT_LIST + String.valueOf(id);
        } else {
            url = URLs.BASE_URL + URLs.DOWNPORT_LIST + String.valueOf(id);
        }
        OkHttpUtils
                .post()
                .tag(this)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        portNameList.clear();
                        portIdList.clear();
                        List<PortBean> portList = new Gson().fromJson(response, new TypeToken<ArrayList<PortBean>>() {
                        }.getType());
                        for (PortBean port : portList) {
                            portNameList.add(port.getText());
                            portIdList.add(port.getId());
                        }
                        port_name.setText(portNameList.get(0));
                    }
                });
    }

    public void getPortDetail(int id, final int type) {
        String url;
        if (type == 0) {
            url = URLs.BASE_URL + URLs.PORT_DETAIL_UP_INFO;
        } else {
            url = URLs.BASE_URL + URLs.PORT_DETAIL_DOWN_INFO;
        }
        OkHttpUtils
                .post()
                .url(url)
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .addParams("pid", String.valueOf(id))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("获取数据异常" + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Map<String, String> result = JsonUtils.toHashMap("{" + response + "}");
                            if (result == null) {
                                showToast("暂无数据");
                            }
                            if (type == 0) {
                                portInfo1.setText(result.get("up_run_status"));
                                portInfo2.setText(result.get("down_port_name"));
                                portInfo3.setText(result.get("district"));
                                portInfo4.setText(result.get("fscode"));
                                portInfo5.setText(result.get("site"));
                                portInfo6.setText(result.get("up_max_flow"));
                                portInfo7.setText(result.get("up_midfreq"));
                                portInfo8.setText(result.get("up_qam"));
                                portInfo9.setText(result.get("up_width"));
                                portInfo10.setText(result.get("snr"));
                                portInfo11.setText(result.get("ber"));
                                portInfo12.setText(result.get("npa"));
                            } else {
                                downPortInfo1.setText(result.get("down_run_status"));
                                downPortInfo2.setText(result.get("down_max_flow"));
                                downPortInfo3.setText(result.get("down_midfreq"));
                                downPortInfo4.setText(result.get("down_qam"));
                                downPortInfo5.setText(result.get("down_out_level"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getChartDetail(final int portId, final int chartType){
        String url = null;
        switch (chartType) {
            case 1:
                url = URLs.BASE_URL + URLs.PORT_SNR_INFO;
                break;
            case 2:
                url = URLs.BASE_URL + URLs.PORT_BER_INFO;
                break;
            case 3:
                url = URLs.BASE_URL + URLs.PORT_NPA_INFO;
                break;
        }
        OkHttpUtils
                .post()
                .tag(this)
                .url(url)
                .addHeader("User-Agent", "topway/2.03_13/Android/" + Build.VERSION.RELEASE + "/" + Build.MODEL + "1234567890123/4562455")
                .addParams("pid", String.valueOf(portId))
                .addParams("date1", TimeUtils.getHourBefore(6))
                .addParams("date2",TimeUtils.getCurrentTime())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<Float> point = JsonUtils.toList(response,chartType);
                        showToast(""+point.size());
                        showToast(point.toString());
                        Log.e("ABCDEFG",""+point.size());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
