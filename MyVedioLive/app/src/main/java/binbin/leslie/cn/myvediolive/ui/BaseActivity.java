package binbin.leslie.cn.myvediolive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import binbin.leslie.cn.myvediolive.constant.Constant;

/**
 * Created by pc on 2017/3/17.
 */

public class BaseActivity extends FragmentActivity {
    private static final String TAG = "TAG";

    Toast toast;

    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(this,"",Toast.LENGTH_LONG);
        init();
    }

    private void init() {

    }

    public void toast(String text){
        if(!TextUtils.isEmpty(text)){
            toast.setText(text);
            toast.show();
        }
    }

    public void toastAndLog(String text,String log){
        toast(text);
        log(log);
    }

    public void toastAndLog(String text,int arg0,String arg1){
        toast(text);
        log(text+",错误代码:"+arg0+","+arg1);
    }

    public void log(String log){
        if(Constant.DEBUG){
            Log.d(TAG,this.getClass().getSimpleName()+"打印的内容:"+log);
        }
    }

    //界面的跳转
    public void jump(Class clazz,boolean isFinished){
        Intent intent = new Intent(this,clazz);
        jump(intent,isFinished);
    }

    public void jump(Intent intent,boolean isFinish){
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }

    public Boolean isEmpty(EditText... list){

        for(EditText et:list){
            String text = et.getText().toString().trim();
            if(TextUtils.isEmpty(text)){
                et.setError("请输入完整!");
                return true;
            }
        }

        return false;
    }
}
