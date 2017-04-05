package cn.tedu.musiclist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.tedu.musiclist2.R;

public class MainActivity extends Activity {
	private ListView lvMusics;
	private MusicAdapter adapter;
	private int page;
	private AlertDialog dialog;

	private void setupView(){
		lvMusics = (ListView) findViewById(R.id.lvMusics);
		int page = 1;
		adapter = new MusicAdapter(this,MusicBiz.getMusics(page));
		lvMusics.setAdapter(adapter);
		
		dialog = new Builder(this).setIcon(android.R.drawable.ic_dialog_info).setTitle("“Ù¿÷œÍ«È")
				.setPositiveButton("»∑∂®",null).create();
	}
	
	private void addListener(){
		lvMusics.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Music m = adapter.getItem(position);
				dialog.setMessage(m.toString());
				dialog.show();
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
