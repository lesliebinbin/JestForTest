package firstdemo.as.tedu.cn.myapplication.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by pc on 2017/3/10.
 */

public class MyUser extends BmobObject implements Serializable{
    private String avatar;//用户选择的头像在服务器上的保存地址
    private String name;
    private Boolean gender;//性别true男生,false女生
    private String password;

    public MyUser(String avatar, String name, Boolean gender, String password) {
        this.avatar = avatar;
        this.name = name;
        this.gender = gender;
        this.password = password;
    }
    public MyUser() {

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
