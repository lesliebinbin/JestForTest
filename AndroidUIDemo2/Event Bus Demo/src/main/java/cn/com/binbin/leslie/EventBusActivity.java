package cn.com.binbin.leslie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends Activity {


    private TextView tvResult;
    private Button btnSend;
    private Button btnSticky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        initView();
        initData();
    }

    private void initData() {
        //3.注册广播
        EventBus.getDefault().register(EventBusActivity.this);


    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.event_bus_result);
        btnSend = (Button) findViewById(R.id.bt_event_bus_send);
        btnSticky = (Button) findViewById(R.id.bt_event_bus_sticky);
    }

    //跳转到发送页面
    public void doSend(View view){
        Intent intent = new Intent(this,EventBusSendActivity.class);
        startActivity(intent);
    }
    //发送粘性事件到发送页面
    public void doSticky(View view){
        //2.发送粘性事件
        EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));

        //3.跳转到发送数据页面
        Intent intent = new Intent(this,EventBusSendActivity.class);
        startActivity(intent);
        finish();
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(MessageEvent event){
        //显示接收的消息
        tvResult.setText(event.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //2.解注册
        EventBus.getDefault().unregister(EventBusActivity.this);
    }
}
