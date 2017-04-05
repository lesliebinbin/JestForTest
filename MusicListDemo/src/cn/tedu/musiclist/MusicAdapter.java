package cn.tedu.musiclist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {
	private ArrayList<Music> musics;
	private LayoutInflater inflater;
	
	public MusicAdapter(Context context,ArrayList<Music> musics) {
		super();
		if(musics!=null)
		this.musics = musics;
		else
			this.musics = new ArrayList<Music>();
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musics.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return musics.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return musics.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//加载或者复用
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.music_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvAlbum = (TextView) convertView.findViewById(R.id.tvAlbum);
			holder.tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
			holder.tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//获取指定位置的数据
		Music m = musics.get(position);
		//将数据写入到item
		holder.tvName.setText(m.getName());
		holder.tvAlbum.setText(m.getAlbum());
		holder.tvArtist.setText(m.getArtist());
		holder.tvDuration.setText(m.getDuration());
		//返回item view
		return convertView;
	}
	
	class ViewHolder{
		private TextView tvName;
		private TextView tvDuration;
		private TextView tvArtist;
		private TextView tvAlbum;
	}

}
