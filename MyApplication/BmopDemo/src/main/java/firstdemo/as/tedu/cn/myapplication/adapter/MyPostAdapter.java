package firstdemo.as.tedu.cn.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.DeleteListener;
import firstdemo.as.tedu.cn.myapplication.R;
import firstdemo.as.tedu.cn.myapplication.bean.MyPost;
import firstdemo.as.tedu.cn.myapplication.bean.MyUser;
import firstdemo.as.tedu.cn.myapplication.ui.PostActivity;

public class MyPostAdapter extends MyBaseAdapter<MyPost>{


	private MyUser mUser;

	public MyPostAdapter(Context context, List<MyPost> list,MyUser user) {
		super(context, list);
		mUser = user;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.item_show,parent,false);
		}
		final ViewHolder holder = ViewHolder.getViewHolder(convertView);
		final MyPost myPost = getItem(position);
		MyUser myUser = myPost.getUser();
		String avatar = myUser.getAvatar();
		holder.tv_title.setText(myPost.getTitle());
		holder.tv_date.setText(myPost.getUpdatedAt());
		holder.tv_content.setText(myPost.getContent());
		holder.tv_name.setText(myUser.getName());

		//显示作者头像
		if(TextUtils.isEmpty(avatar)){
			holder.iv_avatar.setImageResource(R.drawable.ic_launcher);
		}else{
			ImageLoader.getInstance().displayImage(avatar,holder.iv_avatar);
		}
		if(mUser.getObjectId().equals(myUser.getObjectId())){
			holder.btn_delete.setVisibility(View.VISIBLE);
			holder.btn_modify.setVisibility(View.VISIBLE);
		}else{
			holder.btn_delete.setVisibility(View.GONE);
			holder.btn_modify.setVisibility(View.GONE);
		}

		//修改帖子
		holder.btn_modify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,PostActivity.class);
				intent.putExtra("from","update");
				intent.putExtra("post",myPost);
				mContext.startActivity(intent);
			}
		});


		//删除帖子
		holder.btn_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//点击删除帖子
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("删除确认");
				builder.setIcon(android.R.drawable.ic_menu_info_details);
				builder.setMessage("您确认要删除该帖子么");
				builder.setNegativeButton("取消",null);
				builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						//删除帖子
//						myPost.delete(mContext,new DeleteListener() {
//
//							@Override
//							public void onSuccess() {
//								// TODO Auto-generated method stub
//								remove(myPost);
//								Toast.makeText(mContext,"删除成功",1).show();
//							}
//
//							@Override
//							public void onFailure(int arg0, String arg1) {
//								// TODO Auto-generated method stub
//								Toast.makeText(mContext,"删除失败:"+arg0+arg1,1).show();
//							}
//						});

						MyPost newPost = new MyPost();
						newPost.delete(mContext, myPost.getObjectId(), new DeleteListener() {
							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								remove(myPost);
								Toast.makeText(mContext,"删除成功",1).show();
							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub
								Toast.makeText(mContext,"删除失败:"+arg0+arg1,1).show();
							}
						});

				}
			});

				builder.create().show();
		}
	});


		return convertView;
}

static class ViewHolder{
	@Bind(R.id.iv_avatar)
	ImageView iv_avatar;

	@Bind(R.id.tv_title)
	TextView tv_title;

	@Bind(R.id.tv_date)
	TextView tv_date;

	@Bind(R.id.tv_content)
	TextView tv_content;

	@Bind(R.id.btn_modify)
	Button btn_modify;

	@Bind(R.id.btn_delete)
	Button btn_delete;

	@Bind(R.id.tv_name)
	TextView tv_name;

	public ViewHolder(View convertView){
		ButterKnife.bind(this,convertView);
	}

	public static ViewHolder getViewHolder(View convertView){
		ViewHolder holder = (ViewHolder) convertView.getTag();
		if(holder==null){
			holder= new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		return holder;
	}

}

}
