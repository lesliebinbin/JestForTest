package firstdemo.as.tedu.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };

    @Bind(R.id.listView)
    ParallaxListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initListView();
    }

    private void initListView() {

        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);//永远不显示那个蓝色阴影

        View headerView = View.inflate(this,R.layout.layout_header,null);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.imageView);
        mListView.setParallaxImageView(imageView);
        mListView.addHeaderView(headerView);
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
        indexArr));
    }
}
