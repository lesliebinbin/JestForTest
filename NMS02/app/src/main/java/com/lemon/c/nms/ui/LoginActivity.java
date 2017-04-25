package com.lemon.c.nms.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lemon.c.nms.R;
import com.lemon.c.nms.network.URLs;
import com.lemon.c.nms.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class LoginActivity extends BaseActivity {

    public static final  String USERNAME = "username";
    public static final  String PASSWORD = "password";

    private Button btn_login;
    //private TextInputLayout et_username,et_password;
    private EditText et_username,et_password;
    private String username,password;


    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                hideKeyboard();
                //username = et_username.getEditText().getText().toString();
                //password = et_password.getEditText().getText().toString();
                username = et_username.getText().toString();
                password = et_password.getText().toString();

                if(!validateUserName(username)) {
                    et_username.setError("请输入用户名!");
                    return;
                }
                if(password == null){
                    et_password.setError("请输入密码!");
                } else {
                    if(!validatePassWord(password)){
                        et_password.setError("你的密码长度不足6位数");
                    } else {
                        doLogin(username,password);
                    }
                }
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login2;
    }

    @Override
    protected void initParams(Bundle bundle) {
        showClassic(false);
        showBackIcon(false);
        setToolbarTitle("天威综合网管手机客户端");
    }

    @Override
    public void initView(View view) {
        btn_login =$(R.id.btn_login);
        et_username = $(R.id.username);
        et_password = $(R.id.password);
    }

    @Override
    public void setListener() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    public boolean validateUserName(String username){
        return username.length() > 0;
    }
    public boolean validatePassWord(String password){
        return password.length() > 5;
    }

    public void doLogin(final String username,final String password){
                String url = URLs.BASE_URL + URLs.LOGIN_VALIDATE_HTTP;
                OkHttpUtils
                        .post()
                        .url(url)
                        .addParams("username", username)
                        .addParams("password", password)
                        .addParams("opt","ajax")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                showToast("网络异常，登录失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if(response.trim().equals("1,1") || response.trim().equals("1,0")){
                                    showToast("登录成功");
                                    SPUtils.put(LoginActivity.this,USERNAME,username);
                                    SPUtils.put(LoginActivity.this,PASSWORD,password);
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                } else if(response.trim().equals("0")){
                                    showToast("您输入的密码有误");
                                } else if(response.trim().equals("2")){
                                    showToast("您输入的用户名不存在！");
                                }
                            }
                        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
