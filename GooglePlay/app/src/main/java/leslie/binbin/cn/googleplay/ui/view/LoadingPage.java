package leslie.binbin.cn.googleplay.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.manager.ThreadManager;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * Created by pc on 2017/4/2.
 * 根据当前状态显示当前不同页面的自定义控件
 * -未加载
 * -加载中
 * -加载失败
 * -数据为空
 * -加载成功
 */
public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;//未加载
    private static final int STATE_LOAD_LOADING = 2;//正在加载
    private static final int STATE_LOAD_ERROR = 3;//加载失败
    private static final int STATE_LOAD_EMPTY = 4;//数据为空
    private static final int STATE_LOAD_SUCCESS = 5;//加载成功

    private int myCurrentState = STATE_LOAD_UNDO;//当前状态(默认状态)
    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);//将加载中的布局添加给帧布局
        }

        //初始化加载失败的布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);

            //点击重试,事件
            addView(mErrorPage);
            Button button = (Button) findViewById(R.id.btn_retry);
            button.setOnClickListener((view) -> {
                        //重新加载数据
                        Toast.makeText(UIUtils.getContext(), "被点击了", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
            );
        }

        //初始化数据为空的布局
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }
        showRightPage();
    }


    //根据当前状态决定显示哪个布局
    private void showRightPage() {
        mLoadingPage.setVisibility(myCurrentState == STATE_LOAD_UNDO || myCurrentState == STATE_LOAD_LOADING ? View.VISIBLE : View.GONE);
        mErrorPage.setVisibility(myCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        mEmptyPage.setVisibility(myCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        //当成功布局为空,并且当前状态为空,才初始化成功的布局
        if (mSuccessPage == null && myCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();

            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }

        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(myCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    //开始加载数据
    public void loadData() {

        ThreadManager.getThreadPool().execute(() -> {
            if (myCurrentState != STATE_LOAD_LOADING) {//如果当前没有加载就开始加载数据
                myCurrentState = STATE_LOAD_LOADING;
                final ResultState resultState = onLoad();
                //根据最新的状态来刷新页面
                UIUtils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultState != null) {
                            myCurrentState = resultState.getState();//加载结束后,更新网络状态
                            showRightPage();
                        }
                    }
                });
            }
        });


    }

    //加载成功后显示的布局,必须由调用者来实现
    public abstract View onCreateSuccessView();

    //加载网络数据,返回值表示网络结束后的状态

    public abstract ResultState onLoad();


    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
