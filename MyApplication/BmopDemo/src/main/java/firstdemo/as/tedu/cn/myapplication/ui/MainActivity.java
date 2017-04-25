package firstdemo.as.tedu.cn.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import firstdemo.as.tedu.cn.myapplication.R;
import firstdemo.as.tedu.cn.myapplication.bean.MyUser;

public class MainActivity extends Activity {

    @Bind(R.id.et_main_username)
    EditText mEtMainUsername;
    @Bind(R.id.et_main_password)
    EditText mEtMainPassword;
    @Bind(R.id.btn_main_login)
    Button mBtnMainLogin;
    @Bind(R.id.btn_main_register)
    Button mBtnMainRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_main_login)
    public void doLogin(View view){
        String username = mEtMainUsername.getText().toString().trim();
        final String password = mEtMainPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            return;
        }

        //以用户输入的name作为查询条件到服务器数据库MyUser数据表中去查找
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        //查询条件,MyUser数据表中是否有数据记录及name字段值与用户在etname输入的值所一致
        query.addWhereEqualTo("name",username);

        query.findObjects(this,new FindListener<MyUser>() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub

                Toast.makeText(MainActivity.this,"登录失败,原因如下:"+arg0+arg1,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(List<MyUser> arg0) {
                // TODO Auto-generated method stub
                //服务器正常的响应了查询请求,并不代表一定返回一个查询正确的结果
                if(arg0!=null&&arg0.size()>0){
                    //数据表中有符合条件的记录
                    MyUser user = arg0.get(0);
                    //比对密码
                    String sha = new String(Hex.encodeHex(DigestUtils.sha(password)));
                    if(user.getPassword().equals(sha)){
                        //密码也正确
                        //登录成功,跳转界面
                        Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //数据表中没有用户输入用户名相匹配的内容
                    Toast.makeText(MainActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @OnClick(R.id.btn_main_register)
    public void doRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
