package binbin.leslie.cn.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import binbin.leslie.cn.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    Employee mEmployee = new Employee("Zhai", "Mark", true);
    private ActivityMainBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //binding.setEmployee(new Employee("张三","李四"));这种是可以的,下面也可以
        mBinding.viewStub.getViewStub().inflate();
        mBinding.setVariable(BR.employee, mEmployee);
        mBinding.setPresenter(new Presenter());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();
            }
        }, 5000);
    }

    public class Presenter {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEmployee.setFirstName(s.toString());
            //mBinding.setEmployee(mEmployee);
        }

        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "操你妈逼", Toast.LENGTH_SHORT).show();
        }

        public void onClickListenerBind(Employee employee) {
            Toast.makeText(MainActivity.this, employee.getLastName(), Toast.LENGTH_SHORT).show();
        }
    }


}
