package cn.com.binbin.leslie;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusSendActivity extends Activity {

    private TextView tvResult;
    private Button btnSend;
    private Button btnSticky;

    boolean isFirstFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);
        initView();
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.event_bus_result);
        btnSend = (Button) findViewById(R.id.bt_event_bus_send_main);
        btnSticky = (Button) findViewById(R.id.btn_receiver_sticky);
    }

    public void doSendInMain(View view){
        //4.发送消息
        EventBus.getDefault().post(new MessageEvent("主线程发送过来的:草泥马勒个逼"));
        finish();
    }

    //4.注册粘性事件
    public void doReceiverSticky(View view){
        if(isFirstFlag) {
            EventBus.getDefault().register(this);
            isFirstFlag = false;
        }
    }

    //3.接收粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void receiveStickyEvent(StickyEvent event){
        tvResult.setText(event.msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
