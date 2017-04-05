package leslie.binbin.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void doClick(View view){
        LoadingPage page = new LoadingPage_(this);
        page.testForAnnotation();
    }


}
