package binbin.leslie.cn.ndk_demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    static{
        System.loadLibrary("hello");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,getStringFromC(), Toast.LENGTH_SHORT).show();
    }

    public static native String getStringFromC();
}
