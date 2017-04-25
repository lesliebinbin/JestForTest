package binbin.leslie.cn.myvediolive.ui;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import binbin.leslie.cn.myvediolive.R;
import binbin.leslie.cn.myvediolive.presenter.ILoginPresenter;
import binbin.leslie.cn.myvediolive.presenter.impl.LoginPresenterImpl;
import binbin.leslie.cn.myvediolive.view.ILoginView;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements ILoginView{


    private android.widget.ProgressBar loginprogress;
    private android.widget.AutoCompleteTextView email;
    private android.widget.EditText password;
    private android.widget.Button emailsigninbutton;
    private android.widget.LinearLayout emailloginform;
    private android.widget.ScrollView loginform;
    private TextView tvRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.loginform = (ScrollView) findViewById(R.id.login_form);
        this.emailloginform = (LinearLayout) findViewById(R.id.email_login_form);
        this.emailsigninbutton = (Button) findViewById(R.id.email_sign_in_button);
        this.password = (EditText) findViewById(R.id.password);
        this.email = (AutoCompleteTextView) findViewById(R.id.email);
        this.loginprogress = (ProgressBar) findViewById(R.id.login_progress);
        tvRegist = (TextView) findViewById(R.id.go_to_regist);
        // Set up the login form.

        initLoginButtonAndRegistPath();

    }

    private void initLoginButtonAndRegistPath() {
        emailsigninbutton.setOnClickListener((view)->{
            if(isEmpty(password,email)){
                return;
            }
            String username = email.getText().toString().trim().toLowerCase();
            log(username);
            String pwd = password.getText().toString().trim();
            log(pwd);
            ILoginPresenter presenter = new LoginPresenterImpl(this);
            presenter.pRequestToLogin(username,pwd);
        });

        tvRegist.setOnClickListener((view)-> jump(RegistActivity.class,true));
    }


    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        jump(MainActivity.class,true);
    }

    @Override
    public void onLoginFailed(String error) {
        toastAndLog("登录失败",error);
    }
}

