package leslie.binbin.cn.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void doClick(View view){
        // Use MODE_WORLD_READABLE and/or MODE_WORLD_WRITEABLE to grant access to other applications
        SharedPreferences preferences = getSharedPreferences("YourPreferencesName", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("yourPreferenceKey", "Your Preference Value");
        editor.commit();
        LoadingPage page = new LoadingPage_(this);
        page.testForAnnotation();
    }


}
