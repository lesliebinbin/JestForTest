package cn.tedu.mobilesafe.chapter01.activity;


import java.io.File;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;

import cn.tedu.mobilesafe.R;
import cn.tedu.mobilesafe.chapter03.activity.AppLockActivity;
import cn.tedu.mobilesafe.chapter03.activity.CommonNumberActivity;
import cn.tedu.mobilesafe.chapter03.activity.QueryLocationActivity;
import cn.tedu.mobilesafe.chapter03.util.SmsBackupUtils;
import cn.tedu.mobilesafe.globalapp.MobileGuardApplication;

@ContentView(R.layout.activity_advance_tools)
public class AdvanceToolsActivity extends Activity {
    CheckBox mlocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }


    @Event(value = {R.id.btn_query_location, R.id.common_number_query
            , R.id.btn_backup_sms, R.id.btn_lock})
    private void doClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_location://电话归属地查询
                startActivity(new Intent(this, QueryLocationActivity.class));
                break;
            case R.id.btn_backup_sms:
                showSmsBackUpDialog();
                break;
            case R.id.common_number_query:
                startActivity(new Intent(this, CommonNumberActivity.class));
                break;
            case R.id.btn_lock:
                startActivity(new Intent(this, AppLockActivity.class));
                break;
        }


    }


    private void showSmsBackUpDialog() {
        // TODO Auto-generated method stub
        //带进度条的对话框
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIcon(R.drawable.ic_launcher);
        dialog.setTitle("短信备份");
        //指定进度条为水平样式
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        //直接调用短信备份方法
        x.task().run(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator
                        + "msms.xml";
                SmsBackupUtils.backup(MobileGuardApplication.getApp()
                        , path, dialog);
                dialog.dismiss();
            }
        });
    }

}
