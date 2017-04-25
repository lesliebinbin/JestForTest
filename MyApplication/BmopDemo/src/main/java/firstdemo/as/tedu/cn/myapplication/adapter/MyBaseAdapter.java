package firstdemo.as.tedu.cn.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter{

	 List <T> mList;
	 Context mContext;
	 LayoutInflater mInflater;
	
	public MyBaseAdapter(Context context,List<T> list) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mList = list;
		mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 将新数据添加到ListView
	 * @param list
	 * @param isClear true则表示覆盖
	 * 				  false则表示追加
	 * 
	 */
	public void addAll(List<T> list,boolean isClear){
		if(isClear){
			mList.clear();
		}
		
		mList.addAll(list);
		notifyDataSetChanged();
	}
	
	/**
	 * 追加一个单独的数据到mList当中
	 * @param data
	 */
	public void add(T data){
		mList.add(data);
		notifyDataSetChanged();
	}
	
	/**
	 * 移除单个ListView中所有的数据
	 */
	public void removeAll(){
		mList.clear();
		notifyDataSetChanged();
	}
	
	public void remove(T data){
		mList.remove(data);
		notifyDataSetChanged();
	}
}
