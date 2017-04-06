package binbin.leslie.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    static{
        System.loadLibrary("leslie");
    }

    public static native void updateFile(String path);

    public static native int[] updateIntArray(int[] data);
    public static native int[] updateIntArray2(int[] data);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC
        ),"leslie.txt");
        updateFile(file.getAbsolutePath());

        int[] data = {1,2,3,4,5};
        data = updateIntArray2(data);
        for(int i=0;i<data.length;i++) {
            Log.d(TAG,"data"+i+":"+data[i] );
        }
    }
}
