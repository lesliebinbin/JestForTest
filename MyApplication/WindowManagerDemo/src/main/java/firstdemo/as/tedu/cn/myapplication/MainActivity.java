package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv_window_info)
    TextView mTvWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTvWindow.setText("当前手机宽度是:"+getScreenW(this)+"px\n");
        mTvWindow.append("当前手机高度是:"+getScreenH(this)+"px\n");
    }

    public static int[] getScreenHW2(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int[] HW = {height,width};
        return HW;
    }

    public static int getScreenH(Context context){
        return getScreenHW2(context)[0];
    }
    public static int getScreenW(Context context){
        return getScreenHW2(context)[1];
    }
}
