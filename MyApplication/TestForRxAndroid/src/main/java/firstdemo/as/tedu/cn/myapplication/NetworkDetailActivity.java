package firstdemo.as.tedu.cn.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NetworkDetailActivity extends AppCompatActivity {

    private static final String USER_KEY = "network_detail_activity.user";

    public static Intent from(Context context,String username){
        Intent intent = new Intent(context,NetworkActivity.class);
        intent.putExtra(USER_KEY,username);
        return intent;
    }

    @Bind(R.id.network_rv_list)
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_detail);
        ButterKnife.bind(this);

        //设置布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        //设置Adapter
        RepoListAdapter adapter = new RepoListAdapter();
        NetworkWrapper.getRepoInfo(getIntent().getStringExtra(USER_KEY), adapter);
        mRecyclerView.setAdapter(adapter);
    }
}
