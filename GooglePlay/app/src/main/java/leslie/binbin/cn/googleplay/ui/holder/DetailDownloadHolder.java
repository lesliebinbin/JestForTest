package leslie.binbin.cn.googleplay.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.domain.DownloadInfo;
import leslie.binbin.cn.googleplay.manager.DownloadManager;
import leslie.binbin.cn.googleplay.ui.view.ProgressHorizontal;
import leslie.binbin.cn.googleplay.utils.UIUtils;


/**
 * 下载详情页模块
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObsever
        , View.OnClickListener {

    private DownloadManager mDM;
    private int mCurrentState;
    private float mProgress;
    private FrameLayout flProgress;
    private Button btnDownload;
    private ProgressHorizontal pbProgress;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);

        btnDownload = (Button) view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        //初始化自定义进度条
        flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);
        flProgress.setOnClickListener(this);

        pbProgress = new ProgressHorizontal(UIUtils.getContext());

        pbProgress.setProgressBackgroundResource(R.drawable.progress_bg);//进度条背景

        pbProgress.setProgressResource(R.drawable.progress_normal);//进度条图片

        pbProgress.setProgressTextColor(Color.WHITE);//进度条文字颜色

        pbProgress.setProgressTextSize(UIUtils.dip2px(16));//进度文字的大小

        //宽高填充父窗体
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        //给帧布局添加进度条
        flProgress.addView(pbProgress, params);

        mDM = DownloadManager.getInstance();
        mDM.registerObserver(this);//注册观察者,监听状态和进度变化
        return view;
    }

    //根据应用信息返回一个下载对象
    @Override
    public void refreshView(AppInfo data) {
        //判断当前应用是否下载过
        DownloadInfo downloadInfo = mDM.getDownloadInfo(data);
        if (downloadInfo != null) {
            //之前下载过
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            //没有下载过
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }

        refreshUI(mCurrentState, mProgress);
    }

    //根据当前的下载进度和状态更新界面
    private void refreshUI(int currentState, float progress) {
        mCurrentState = currentState;
        mProgress = progress;

        switch (mCurrentState) {
            case DownloadManager.STATE_UNDO://未下载
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;

            case DownloadManager.STATE_WAITING://等待下载
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中...");
                break;

            case DownloadManager.STATE_DOWNLOADING://正在下载
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("");
                pbProgress.setProgress(mProgress);//设置下载进度
                break;

            case DownloadManager.STATE_PAUSE://暂停的状态
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("暂停");
                pbProgress.setProgress(mProgress);
                break;

            case DownloadManager.STATE_ERROR://下载失败
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载失败");
                break;

            case DownloadManager.STATE_SUCCESS://下载成功
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;
        }
    }


    //主线程更新UI
    private void refreshUIOnMainThread(DownloadInfo info) {
        //判断下载对象是否是当前的应用
        AppInfo appInfo = getData();
        if (appInfo.id.equals(info.id)) {
            UIUtils.runOnUIThread(() -> refreshUI(info.currentState, info.getProgress()));
        }
    }

    //状态的更新和进度的更新(有可能在子线程,也有可能在子线程)
    @Override
    public void onDownloadStateChange(DownloadInfo info) {
        refreshUIOnMainThread(info);

    }


    @Override
    //子线程里面执行
    public void onDownloadProgressChange(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
            case R.id.fl_progress:
                //根据当前的状态来决定下一步的操作
                if (mCurrentState == DownloadManager.STATE_UNDO
                        || mCurrentState == DownloadManager.STATE_ERROR
                        || mCurrentState == DownloadManager.STATE_PAUSE) {
                    mDM.download(getData());//开始下载
                } else if (mCurrentState == DownloadManager.STATE_DOWNLOADING
                        || mCurrentState == DownloadManager.STATE_WAITING) {
                    mDM.pause(getData());//暂停下载
                } else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
                    mDM.install(getData());
                }
                break;
        }
    }
}

