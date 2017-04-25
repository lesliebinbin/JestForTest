package binbin.leslie.cn.myapplication;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import binbin.leslie.cn.myapplication.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {

    ActivityListBinding mBinding;
    EmployeeAdapter mEmployeeAdapter;

    public class Presenter{
        public void onClickAddItem(View view){
            mEmployeeAdapter.add(new Employee("haha","1",false));
        }

        public void onClickRemoveItem(View view){
            mEmployeeAdapter.remove();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_list);
        /*记住setLayoutManager很重要,如果忘记了设置这一步,可能数据就不显示了*/
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBinding.setPresenter(new Presenter());
        mEmployeeAdapter = new EmployeeAdapter(this);
        mBinding.recyclerView.setAdapter(mEmployeeAdapter);
        mEmployeeAdapter.setListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onEmployeeClick(Employee employee) {
                Toast.makeText(ListActivity.this, employee.getFirstName(), Toast.LENGTH_SHORT).show();
            }
        });
        List<Employee> demoList = new ArrayList<>();
        demoList.add(new Employee("Leslie","wong",false));
        demoList.add(new Employee("Jolin","Young",false));
        demoList.add(new Employee("Ally","Che",false));
        demoList.add(new Employee("Winne","Luo",true));
        mEmployeeAdapter.addAll(demoList);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ListActivity.this,ExpressionActivity.class));
                finish();
            }
        },5000);
    }
}
