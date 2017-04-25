package binbin.leslie.cn.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import binbin.leslie.cn.myapplication.databinding.ActivityTwoWayBinding;

public class TwoWayActivity extends AppCompatActivity {

    ActivityTwoWayBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_two_way);
        FormModel formModel = new FormModel("leslie", "woainvren1");
        mBinding.setModel(formModel);
        formModel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Toast.makeText(TwoWayActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(TwoWayActivity.this, LambadaActivity.class));
                finish();
            }
        }, 3000);

    }
}
