package firstdemo.as.tedu.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NetworkActivity extends Activity {
    @Bind(R.id.network_rv_list)RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        //设置Layout布局管理
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        //设置适配器
        UserListAdapter adapter = new UserListAdapter(this::gotoDetailedPage);
        NetworkWrapper.getUserInfo(adapter);
        mRecyclerView.setAdapter(adapter);

//        RepoListAdapter adapter = new RepoListAdapter();
//        NetworkWrapper.getRepoInfo("SpikeKing",adapter);
//        mRecyclerView.setAdapter(adapter);

    }

    //点击的回调
    public interface UserClickCallback{
        void itemClicked(String name);
    }

    //跳转到库详情界面
    private void gotoDetailedPage(String name){
        startActivity(NetworkDetailActivity.from(this,name));

    }
}
