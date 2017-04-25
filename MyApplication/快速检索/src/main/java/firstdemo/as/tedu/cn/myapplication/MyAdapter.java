package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pc on 2017/3/12.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Friend> mList;

    public MyAdapter(Context context,ArrayList<Friend> list) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Friend getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.adapter_friend,null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);

        //设置数据
        Friend friend = getItem(position);
        String currentWord = friend.getPinyin().substring(0,1);

        if(position>0) {
            String lastWord = getItem(position - 1).getPinyin().substring(0,1);
            //拿当前的首字母和上一个首字母比较
            if(currentWord.equals(lastWord)){
                //说明首字母相同,需要隐藏当前字母的first_word
                holder.first_word.setVisibility(View.GONE);
            }else{
                //不一样,需要显示当前字母
                //由于布局是复用的,所以在显示的时候,需要再次将first_word设置为可见
                holder.first_word.setVisibility(View.VISIBLE);
                holder.first_word.setText(friend.getPinyin().substring(0,1));
            }
        }else{
            holder.first_word.setVisibility(View.VISIBLE);
            holder.first_word.setText(friend.getPinyin().substring(0,1));
        }
        holder.name.setText(friend.getName());

        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.first_word)
        TextView first_word;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }

        public static ViewHolder getHolder(View convertView){
            ViewHolder holder  = (ViewHolder) convertView.getTag();
            if(holder==null){
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
