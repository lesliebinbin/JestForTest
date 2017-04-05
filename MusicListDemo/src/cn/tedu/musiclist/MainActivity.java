package cn.tedu.musiclist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView lvMusics;
	private MusicAdapter adapter;

	private void setupView(){
		lvMusics = (ListView) findViewById(R.id.lvMusics);
		int page = 1;
		adapter = new MusicAdapter(this,MusicBiz.getMusics(page));
		lvMusics.setAdapter(adapter);
	}
	
	private void addListener(){
		lvMusics.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Log.i("info","position:"+position+",id:"+id);
				Music m = (Music) adapter.getItem(position);
				Log.i("info",m.toString());
			}
			
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupView();
		addListener();
	}




}
