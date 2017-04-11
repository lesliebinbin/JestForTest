package leslie.binbin.cn.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.domain.DownloadInfo;
import leslie.binbin.cn.googleplay.http.HttpHelper;
import leslie.binbin.cn.googleplay.manager.DownloadManager;
import leslie.binbin.cn.googleplay.ui.view.ProgressArc;
import leslie.binbin.cn.googleplay.utils.BitmapHelper;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * 首页的Holder已经
 */

public class HomeHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObsever, View.OnClickListener {

    private TextView mTvContent;
    private TextView tvName;
    private TextView tvSize;
    private TextView tvDes;
    private ImageView ivIcon;
    private RatingBar rbStar;

    private BitmapUtils mBitmapUtils;
    private DownloadManager mDM;

    private int mCurrentState;
    private float mProgress;
    private ProgressArc pbProgress;
    private TextView tvDownload;

    @Override
    public View initView() {
        //1.加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //2.初始化控件
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);

        tvDownload = (TextView) view.findViewById(R.id.tv_download);

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        //初始化进度条
        FrameLayout flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);

        pbProgress = new ProgressArc(UIUtils.getContext());
        //设置圆形进度条直径
        pbProgress.setArcDiameter(UIUtils.dip2px(26));
        //设置进度条颜色
        pbProgress.setProgressColor(UIUtils.getColor(R.color.progress));
        //设置进度条宽高布局参数
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                UIUtils.dip2px(27),
                UIUtils.dip2px(27)
        );

        flProgress.addView(pbProgress, params);

        //pbProgress.setOnClickListener(this);
        flProgress.setOnClickListener(this);

        mDM = DownloadManager.getInstance();
        mDM.registerObserver(this);
        return view;


    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tvDes.setText(data.des);
        rbStar.setRating(data.stars);
        mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name=" + data.iconUrl);

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

        refreshUI(mCurrentState, mProgress,data.id);
    }

    @Override
    public void onDownloadStateChange(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    @Override
    public void onDownloadProgressChange(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    @Override
    public void onClick(View v) {
        //根据当前的状态来决定下一步的操作
        switch (v.getId()) {
            case R.id.fl_progress:
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

    //根据当前的下载进度和状态更新界面
    private void refreshUI(int state, float progress,String id) {

        //由于ListView的重用性,要确保刷新之前,确实是同一个应用
        if(!getData().id.equals(id)){
            return;
        }

        mCurrentState = state;
        mProgress = progress;
        switch (state) {
            case DownloadManager.STATE_UNDO:
                //自定义进度条背景
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                //没有进度
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText(UIUtils.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_WAITING:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                //等待模式
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tvDownload.setText(UIUtils.getString(R.string.app_state_waiting));
                break;
            case DownloadManager.STATE_DOWNLOADING:
                pbProgress.setBackgroundResource(R.drawable.ic_pause);
                //下载中模式
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                pbProgress.setProgress(progress, true);
                tvDownload.setText((int) (progress * 100) + "%");
                break;
            case DownloadManager.STATE_PAUSE:
                pbProgress.setBackgroundResource(R.drawable.ic_resume);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                break;
            case DownloadManager.STATE_ERROR:
                pbProgress.setBackgroundResource(R.drawable.ic_redownload);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText(UIUtils.getString(R.string.app_state_error));
                break;
            case DownloadManager.STATE_SUCCESS:
                pbProgress.setBackgroundResource(R.drawable.ic_install);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload
                        .setText(UIUtils.getString(R.string.app_state_downloaded));
                break;

            default:
                break;
        }
    }

    //主线程更新UI
    private void refreshUIOnMainThread(DownloadInfo info) {
        //判断下载对象是否是当前应用
        AppInfo appInfo = getData();

        if (appInfo.id.equals(info.id)) {
            UIUtils.runOnUIThread(() -> refreshUI(info.currentState, info.getProgress(),info.id));
        }
    }
}
