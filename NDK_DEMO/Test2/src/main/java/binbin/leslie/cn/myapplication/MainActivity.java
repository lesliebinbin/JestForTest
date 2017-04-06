package binbin.leslie.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {


    public static native String getHelloFromBinbin();

    static {
        System.loadLibrary("Test2");
    }

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView1.setText(getHelloFromBinbin());
    }
}
