package firstdemo.as.tedu.cn.myapplication.bean;

import cn.bmob.v3.BmobObject;

public class MyPost extends BmobObject{
	
	String title;//帖子的标题
	String content;//帖子的正文
	MyUser user;//帖子的作者
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MyUser getUser() {
		return user;
	}
	public void setUser(MyUser user) {
		this.user = user;
	}
	
	
}
