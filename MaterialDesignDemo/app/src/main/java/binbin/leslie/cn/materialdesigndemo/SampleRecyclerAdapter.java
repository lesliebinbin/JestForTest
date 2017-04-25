package binbin.leslie.cn.materialdesigndemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pc on 2017/3/28.
 */

public class SampleRecyclerAdapter extends RecyclerView.Adapter<SampleRecyclerAdapter.VHolder>{

    private static final ArrayList<SampleModel> sampleData =
            DemoApp.getSampleData(20);
    //用于创建控件
    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //获得列表项控件(LinearLayout对象)
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_basic_item,parent,false);
        return new VHolder(item);
    }

    //为控件设置数据
    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        final SampleModel rawData = sampleData.get(position);

        holder.textViewSample.setText(rawData.getSampleText());
        holder.itemView.setTag(rawData);
    }

    public void removeData(int position){
        sampleData.remove(position);
        //通知view控件某个item控件被删除了
        notifyItemRemoved(position);
    }

    //指定的位置添加一个新的item
    public void addItem(int positionToAdd){
        sampleData.add(positionToAdd,new SampleModel("新的列表项"+new Random()
        .nextInt(10000)));

        notifyItemInserted(positionToAdd);
    }

    @Override
    public int getItemCount() {
        return sampleData.size();
    }

    public static class VHolder extends RecyclerView.ViewHolder{

        private final TextView textViewSample;

        public VHolder(View itemView) {
            super(itemView);
            textViewSample = (TextView) itemView.findViewById(R.id.textViewSample);
        }
    }
}
