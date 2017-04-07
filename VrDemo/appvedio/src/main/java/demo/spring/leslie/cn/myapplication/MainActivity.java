package demo.spring.leslie.cn.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class MainActivity extends Activity {

    private VrVideoView mVrVideoView;
    private VedioTask mTask;
    private SeekBar mSeekBar;
    private TextView mTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //步骤一.搭建vr全景开发视频
        //1.1导入需要的三个库 common commonwidget videowidget
        //1.2依赖三个库
        //1.3准备显示使用到的全景视频,放到assets目录下面/
        //1.4配置大内存选项 可以使用最大内存
        //步骤二.加载视频到内存中,再使用控件显示
        //2.1布局全景视频控件
        mVrVideoView = (VrVideoView) findViewById(R.id.vr_vedio_view);
        //2.2加载全景视频
        mTask = new VedioTask();
        mTask.execute("congo_2048.mp4");
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mTimeText = (TextView) findViewById(R.id.time);
    }

    //2.2.1创建异步任务占用主线程
    private class VedioTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            //2.2.2把文件名取出来进行加载,视频资源来自于资产目录
            VrVideoView.Options options = new VrVideoView.Options();
            //立体的视频资源,上半画面显示在左眼,下半画面显示在右眼
            options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
            //FORMAT_DEFAULT视频资源来自asset/sd卡
            //FORMAT_HLS视频资源来自网络流媒体,例如直播
            options.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;
            try {
                //步骤四,编写进度显示等业务逻辑
                mVrVideoView.setEventListener(new VrVideoEventListener(){
                    @Override
                    public void onLoadSuccess() {
                        super.onLoadSuccess();
                        Toast.makeText(MainActivity.this, "准备播放视频", Toast.LENGTH_SHORT).show();
                        updateProgress();

                    }

                    //4.2加载失败的提示
                    @Override
                    public void onLoadError(String errorMessage) {
                        super.onLoadError(errorMessage);
                        Toast.makeText(MainActivity.this, "视频加载失败:"+errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    //4.3显示播放时长与播放进度
                    //4.3.1布局显示控件 SeekBar与TextView
                    //4.3.2查找出来
                    //4.3.3在onLoadSuccess里面获取时长,视频播放位置
                    //4.3.4在onNewFrame 不断的获取进度之

                    //播放了一个画面onNewFrame就会被调用

                    @Override
                    public void onNewFrame() {
                        super.onNewFrame();
                        updateProgress();
                    }

                    private boolean isPause;
                    //4.4处理播放完成

                    @Override
                    public void onCompletion() {
                        super.onCompletion();
                        mVrVideoView.seekTo(0);
                        mVrVideoView.pauseVideo();
                        updateProgress();
                    }

                    //4.5点击业务
                    @Override
                    public void onClick() {
                        super.onClick();
                        if(isPause){
                            isPause=false;
                            mVrVideoView.playVideo();
                        }else{
                            isPause=true;
                            mVrVideoView.pauseVideo();
                        }
                    }
                });
                //4.6FULLSCREEN_STEREO是头盔
                //mVrVideoView.setDisplayMode(VrVideoView.DisplayMode.FULLSCREEN_STEREO);
                mVrVideoView.loadVideoFromAsset(params[0],options);//参1文件名,参2设置参数
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void updateProgress() {
            long max = mVrVideoView.getDuration();
            long currentPosition = mVrVideoView.getCurrentPosition();
            mSeekBar.setMax((int) max);
            mSeekBar.setProgress((int) currentPosition);
            mTimeText.setText(
                    String.format("%.2f",currentPosition/100f)+
                            "/"+String.format("%.2f",max/100f));
        }
    }

    //步骤三,程序优化,页面退到后台,暂停 页面回到屏幕继续显示, 页面销毁,关闭


    @Override
    protected void onPause() {
        super.onPause();
        if(mVrVideoView!=null){
            mVrVideoView.pauseRendering();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mVrVideoView!=null){
            mVrVideoView.resumeRendering();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVrVideoView!=null){
            mVrVideoView.shutdown();
        }
        if(mTask!=null&&!mTask.isCancelled()){
            mTask.cancel(true);
            mTask=null;
        }
    }
}
