package firstdemo.as.tedu.cn.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import firstdemo.as.tedu.cn.myapplication.R;
import firstdemo.as.tedu.cn.myapplication.adapter.MyPostAdapter;
import firstdemo.as.tedu.cn.myapplication.bean.MyPost;
import firstdemo.as.tedu.cn.myapplication.bean.MyUser;


public class ShowActivity extends Activity {

	MyUser user;//当前登录用户
	
	@Bind(R.id.lv_post)
	ListView lvPost;

	private MyPostAdapter mAdapter;
	
	@Bind(R.id.iv_add)
	ImageView iv_add;
	
	@Bind(R.id.iv_refresh)
	ImageView iv_refresh;

	private List<MyPost> postList;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		ButterKnife.bind(this);
		user = (MyUser) getIntent().getSerializableExtra("user");
		initHeaderView();
		initListView();
	}

	private void initHeaderView() {
		// TODO Auto-generated method stub
		iv_add.setColorFilter(Color.BLUE,Mode.SRC_ATOP);
		iv_refresh.setColorFilter(Color.BLUE,Mode.SRC_ATOP);
	}

	private void initListView() {
		postList = new ArrayList<MyPost>();
		
		mAdapter = new MyPostAdapter(this,postList, user);
		lvPost.setAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh();
	}

	@OnClick(R.id.iv_add)
	public void addPost(View view){
		Intent intent = new Intent(this,PostActivity.class);
		intent.putExtra("user",user);
		intent.putExtra("from","add");
		startActivity(intent);
	}
	
	@OnClick(R.id.iv_refresh)
	public void doRefresh(View view){
		refresh();
	}
	

	private void refresh() {
		BmobQuery<MyPost> query = new BmobQuery<MyPost>();
		
		//设置查询条件
		query.include("user");//告诉服务器,查询的时候把User这个字段也加进来
		//告诉服务器,返回查询数据的时候请按照帖子创建的时间进行倒排序
		query.order("-createdAt");//加减号是倒排序
		query.findObjects(this,new FindListener<MyPost>() {
			
			@Override
			public void onSuccess(List<MyPost> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(ShowActivity.this,"获取数据成功",Toast.LENGTH_LONG).show();
				mAdapter.addAll(arg0,true);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(ShowActivity.this,"获取数据失败",Toast.LENGTH_LONG).show();
				
			}
		});
	}

}
