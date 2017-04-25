package binbin.leslie.cn.materialdesigndemo;

import android.app.Activity;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class MainActivity extends Activity {


    private FrameLayout mDeleteBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeleteBar = (FrameLayout) findViewById(R.id.deleteBar);
        // mDeleteBar
        // 创建右下角圆形按钮
        // 创建RecyclerView控件
        // 创建上方的Delete面板

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider()
        {

            @Override
            public void getOutline(View view, Outline outline)
            {
                // 获取按钮的尺寸
                int fabSize = getResources().getDimensionPixelSize(
                        R.dimen.fab_size);
                // 设置轮廓的尺寸
                outline.setOval(-4, -4, fabSize + 2, fabSize + 2);

            }
        };
        // 获得右下角圆形按钮对象
        View fabView = findViewById(R.id.fab_add);

        fabView.setOutlineProvider(viewOutlineProvider);

        // 获取RecyclerView对象
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        //  创建线性布局管理器（默认是垂直方向）
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //  为RecyclerView指定布局管理对象
        recyclerView.setLayoutManager(layoutManager);

        //  创建列表项分隔线对象
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);

        //  为RecyclerView控件指定分隔线对象
        recyclerView.addItemDecoration(itemDecoration);
        String s = "a";

        final SampleRecyclerAdapter sampleRecyclerAdapter = new SampleRecyclerAdapter();
        recyclerView.setAdapter(sampleRecyclerAdapter);

        //  处理添加按钮的单击事件
        fabView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //  获取第一个可视的Item的位置
                int positionToAdd = layoutManager.findFirstCompletelyVisibleItemPosition();
                //  在该位置插入新的Item
                sampleRecyclerAdapter.addItem(positionToAdd);

            }
        });
        //  处理删除事件
        mDeleteBar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                int positionToRemove = layoutManager.findFirstCompletelyVisibleItemPosition();

                // 删除第一个可视的ite
                sampleRecyclerAdapter.removeData(positionToRemove);

            }
        });

        //  为RecyclerView控件设置滚动事件
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener()
        {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                super.onScrolled(recyclerView, dx, dy);
                //  dx：大于0，向右滚动    小于0，向左滚动
                //  dy：大于0，向上滚动    小于0，向下滚动

                if(dy > 0)
                {
                    //  列表向上滚动，隐藏删除面板
                    if(mDeleteBar.getVisibility() == View.VISIBLE)
                    {
                        hideDeleteBar();
                    }
                }
                else
                {
                    // 列表向下滚动，显示删除面板
                    if(mDeleteBar.getVisibility() == View.GONE)
                    {
                        showDeleteBar();
                    }
                }

            }
        });
    }

    private void showDeleteBar()
    {

        mDeleteBar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_on));

        mDeleteBar.setVisibility(View.VISIBLE);
    }

    private void hideDeleteBar()
    {

        mDeleteBar.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.translate_up_off));

        mDeleteBar.setVisibility(View.GONE);
    }
}
