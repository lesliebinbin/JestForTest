package firstdemo.as.tedu.cn.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tv_retrofit_test)
    TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            getContributiorListA();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getContributiorListA()throws Exception{
        GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
        Call<List<RepositoryBean>> call = gitHubService.queryOrgRepos("guchuanhangOrganization");
        call.enqueue(new Callback<List<RepositoryBean>>() {
            @Override
            public void onResponse(Call<List<RepositoryBean>> call, Response<List<RepositoryBean>> response) {
                List<RepositoryBean> conList=response.body();
                for(RepositoryBean bean:conList){
                    tvTest.append(bean.toString()+"\n");
                }
            }

            @Override
            public void onFailure(Call<List<RepositoryBean>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
