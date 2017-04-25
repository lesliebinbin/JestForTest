package binbin.leslie.cn.myapplication;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import binbin.leslie.cn.myapplication.databinding.ActivityLambadaBinding;

public class LambadaActivity extends AppCompatActivity {

    private ActivityLambadaBinding mBinding;

    public class Presenter{
        public void onEmployeeClick(Employee employee){
            Toast.makeText(LambadaActivity.this, "onEmployeeClick", Toast.LENGTH_SHORT).show();
        }

        public void onEmployeeLongClick(Employee employee,Context context){
            Toast.makeText(context, "onEmployeeLongClick", Toast.LENGTH_SHORT).show();
        }

        public void onFocusChanged(Employee employee){
            Toast.makeText(LambadaActivity.this, "onFocusChanged", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_lambada);
        mBinding.setEmployee(new Employee("Binbin","Wong",false));
        mBinding.setPresenter(new Presenter());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LambadaActivity.this,AnimationActivity.class));
            }
        },2000);
    }
}
