package firstdemo.as.tedu.cn.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import firstdemo.as.tedu.cn.myapplication.R;
import firstdemo.as.tedu.cn.myapplication.bean.MyPost;
import firstdemo.as.tedu.cn.myapplication.bean.MyUser;


public class PostActivity extends Activity {

	private MyUser user;
	
	@Bind(R.id.et_title)
	EditText et_title;
	
	@Bind(R.id.et_content)
	EditText et_content;
	
	@Bind(R.id.btn_post)
	Button btnPost;
	@Bind(R.id.btn_update)
	Button btnUpdate;
	
	String from;//add,update

	private MyPost updatedPost;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		ButterKnife.bind(this);
		user = (MyUser) getIntent().getSerializableExtra("user");
		from = getIntent().getStringExtra("from");
		if("add".equals(from)){
			btnPost.setVisibility(View.VISIBLE);
			btnUpdate.setVisibility(View.GONE);
		}else{
			btnPost.setVisibility(View.GONE);
			btnUpdate.setVisibility(View.VISIBLE);
			updatedPost = (MyPost) getIntent().getSerializableExtra("post");
			et_title.setText(updatedPost.getTitle());
			et_content.setText(updatedPost.getContent());
		}
	}
	
	@OnClick(R.id.btn_update)
	public void update(View view){
		String title = et_title.getText().toString().trim();
		String content = et_content.getText().toString().trim();
		
		
		if(TextUtils.isDigitsOnly(title)||TextUtils.isEmpty(content)){
			Toast.makeText(this,"标题,内容都不能为空",Toast.LENGTH_LONG).show();
			return;
		}

		MyPost newPost = new MyPost();
		newPost.setTitle(title);
		newPost.setContent(content);
		newPost.update(this, updatedPost.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				Toast.makeText(PostActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
		}

			@Override
			public void onFailure(int i, String s) {
				Toast.makeText(PostActivity.this, "更新失败:"+i+s, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	@OnClick(R.id.btn_post)
	public void sendPost(View view){
		String title = et_title.getText().toString().trim();
		String content = et_content.getText().toString().trim();
		if(TextUtils.isDigitsOnly(title)||TextUtils.isEmpty(content)){
			Toast.makeText(this,"标题,内容都不能为空",Toast.LENGTH_LONG).show();
			return;
		}
		MyPost myPost = new MyPost();
		myPost.setTitle(title);
		myPost.setContent(content);
		myPost.setUser(user);
		myPost.save(this,new SaveListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(PostActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
				et_content.setText("");
				et_title.setText("");
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(PostActivity.this,"保存失败,原因如下:"+arg0+arg1,Toast.LENGTH_LONG).show();
			}
		});
	}

	@OnClick(R.id.iv_back)
	public void goBack(View view){
		finish();
	}

}
