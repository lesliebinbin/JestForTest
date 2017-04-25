package com.lemon.c.nms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemon.c.nms.R;
import com.lemon.c.nms.bean.SpectrumBean;

import java.util.List;

/**
 * Created by C on 2016/9/2.
 */
public class SpectrumAdapter extends BaseAdapter{
    private List<SpectrumBean.DataBean> mList;
    private Context context;
    private LayoutInflater inflater;

    public SpectrumAdapter(Context context, List<SpectrumBean.DataBean>list){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_spectrum, null);
            viewHolder.tv_info1 = (TextView) view.findViewById(R.id.spectrum_info1);
            viewHolder.tv_info2 = (TextView) view.findViewById(R.id.spectrum_info2);
            viewHolder.tv_info3 = (TextView) view.findViewById(R.id.spectrum_info3);
            viewHolder.tv_info4 = (TextView) view.findViewById(R.id.spectrum_info4);
            viewHolder.tv_info5 = (TextView) view.findViewById(R.id.spectrum_info5);
            viewHolder.tv_info6 = (TextView) view.findViewById(R.id.spectrum_info6);
            viewHolder.tv_info7 = (TextView) view.findViewById(R.id.spectrum_info7);
            viewHolder.tv_info8 = (TextView) view.findViewById(R.id.spectrum_info8);
            viewHolder.tv_info9 = (TextView) view.findViewById(R.id.spectrum_info9);
            viewHolder.tv_info10 = (TextView) view.findViewById(R.id.spectrum_info10);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SpectrumBean.DataBean spectrum = mList.get(i);
        viewHolder.tv_info1.setText(spectrum.getErr_paper_id());
        viewHolder.tv_info2.setText(spectrum.getDepart_name());
        viewHolder.tv_info3.setText(spectrum.getSr_name()+"机房");
        viewHolder.tv_info4.setText(spectrum.getCreate_time());
        viewHolder.tv_info5.setText(spectrum.getPort_id());
        viewHolder.tv_info6.setText(spectrum.getAlarm_name());
        viewHolder.tv_info7.setText(spectrum.getDistrict());
        viewHolder.tv_info8.setText(spectrum.getFs_code());
        viewHolder.tv_info9.setText(spectrum.getFixed());
        viewHolder.tv_info10.setText(spectrum.getTime());
        return view;
    }

    public class ViewHolder{
        TextView tv_info1,tv_info2,tv_info3,tv_info4,tv_info5,tv_info6,tv_info7,tv_info8,tv_info9,tv_info10;
    }
}
