package binbin.leslie.cn.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import binbin.leslie.cn.myapplication.databinding.ActivityExpressionBinding;


public class ExpressionActivity extends AppCompatActivity {

    ActivityExpressionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Employee employee = new Employee("Leslie","Wong",false);
        employee.setAvatar("http://localhost/day0902/images/info.gif");
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_expression);
        mBinding.setEmployee(employee);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(ExpressionActivity.this,TwoWayActivity.class));
            }
        },3000);
    }
}
