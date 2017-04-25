package com.lemon.c.nms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemon.c.nms.R;
import com.lemon.c.nms.bean.AlertInfoBean;

import java.util.List;

/**
 * Created by C on 2016/8/29.
 */
public class AlertInfoAdapter extends BaseAdapter {
    private List<AlertInfoBean> mList;
    private Context context;
    private LayoutInflater inflater;

    public AlertInfoAdapter(Context context, List<AlertInfoBean>list){
        this.mList = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_alert_info, null);
            viewHolder.tv_name = (TextView) view.findViewById(R.id.cmts_name);
            viewHolder.tv_port = (TextView) view.findViewById(R.id.cmts_portName);
            viewHolder.tv_type = (TextView) view.findViewById(R.id.cmts_alertType);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        AlertInfoBean alertInfo = mList.get(i);
        viewHolder.tv_name.setText(alertInfo.getCmtsName());
        viewHolder.tv_port.setText(alertInfo.getPortName());
        viewHolder.tv_type.setText(alertInfo.getValue());
        return view;
    }

    public class ViewHolder{
        TextView tv_name,tv_port,tv_type;
    }
}
