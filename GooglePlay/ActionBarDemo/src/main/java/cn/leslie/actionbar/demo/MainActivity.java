package cn.leslie.actionbar.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);//加载action bar菜单布局
        return true;
    }

    private void initActionBar(){
        ActionBar actionBar = getActionBar();

        actionBar.setTitle("哈哈哈哈哈哈");//设置标题
        actionBar.setLogo(android.R.drawable.arrow_up_float);//设置logo
        actionBar.setHomeButtonEnabled(true);//设置logo是否可以被点击
        actionBar.setDisplayShowHomeEnabled(false);//隐藏logo

        actionBar.setDisplayHomeAsUpEnabled(true);//显示返回键
    }

    //点击事件的处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "我操了你妈的个逼了呀", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                //左上角home处响应的点击事件
                Toast.makeText(this, "返回了", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
