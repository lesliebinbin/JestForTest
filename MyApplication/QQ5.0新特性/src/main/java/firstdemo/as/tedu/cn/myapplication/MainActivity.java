package firstdemo.as.tedu.cn.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test_activity)
    public void doClick(View view){
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_slide_test)
    public void doSlide(View view){
        Intent intent = new Intent(this,SlideActivity.class);
        startActivity(intent);
    }
}
