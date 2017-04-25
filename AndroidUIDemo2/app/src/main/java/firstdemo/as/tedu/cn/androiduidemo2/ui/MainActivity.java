package firstdemo.as.tedu.cn.androiduidemo2.ui;

import android.app.Activity;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import binbin.leslie.cn.mylibrary2.Lib2Test;
import firstdemo.as.tedu.cn.androiduidemo2.R;
import firstdemo.as.tedu.cn.androiduidemo2.view.CircleImageView;

public class MainActivity extends Activity {



    Integer[] resIds = {R.drawable.img1352
          ,R.drawable.test};


            //,R.drawable.timg,
//            R.mipmap.ic_launcher,R.mipmap.ic_launcher_round};
    private CircleImageView mMBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMBtnTest = (CircleImageView) findViewById(R.id.btn_test);
        RxView.clicks(mMBtnTest).throttleFirst(4, TimeUnit.SECONDS).
                subscribe(aVoid -> System.out.println("你好世界!"));
        Lib2Test.printHahaha();
    }
}
