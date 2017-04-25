package binbin.leslie.cn.myvediolive.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon;

import binbin.leslie.cn.myvediolive.R;
import binbin.leslie.cn.myvediolive.presenter.IRegistPresenter;
import binbin.leslie.cn.myvediolive.presenter.impl.RegistPresenterImpl;
import binbin.leslie.cn.myvediolive.view.IRegistView;


public class RegistActivity extends BaseActivity implements IRegistView{

    private com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon editregisteraccount;
    private com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon editregisternickname;
    private com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon editregisterpassword;
    private android.widget.TextView tvgobacklogin;
    private android.widget.LinearLayout registerlayout;
    private Button btnRegist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        this.registerlayout = (LinearLayout) findViewById(R.id.register_layout);
        this.tvgobacklogin = (TextView) findViewById(R.id.tv_go_back_login);
        this.editregisterpassword = (ClearableEditTextWithIcon) findViewById(R.id.edit_register_password);
        this.editregisternickname = (ClearableEditTextWithIcon) findViewById(R.id.edit_register_nickname);
        this.editregisteraccount = (ClearableEditTextWithIcon) findViewById(R.id.edit_register_account);
        btnRegist = (Button) findViewById(R.id.btn_to_regist);
        initTheRegistButtonAndLoginPath();

    }

    private void initTheRegistButtonAndLoginPath() {
        btnRegist.setOnClickListener((view)-> {
                    requestToRegist();
                }
        );
        tvgobacklogin.setOnClickListener((view)->{
            jump(LoginActivity.class,true);
        });
    }

    @Override
    public void requestToRegist() {
        if(isEmpty(editregisteraccount,editregisternickname,editregisterpassword)){
            return;
        }

        String username = editregisteraccount.getText().toString().trim();
        String nickname = editregisternickname.getText().toString().trim();
        String password = editregisterpassword.getText().toString().trim();

        IRegistPresenter presenter = new RegistPresenterImpl(this);
        presenter.pRequestTORegist(username,nickname,password);
    }

    @Override
    public void onSuccess() {
        toast("注册成功");
        jump(MainActivity.class,true);
    }

    @Override
    public void onFailed(int code, String errorMsg) {
        toastAndLog("注册失败,请稍后重试"+"错误码:"+code+"原因:"+":"+errorMsg,"错误码:"+code+"原因:"+":"+errorMsg);
    }
}
