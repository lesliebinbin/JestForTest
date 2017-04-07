package vrdemo.leslie.cn.vrdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    private VrPanoramaView mPanoramaView;
    private ImageTask mImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //全景图片的浏览功能
        //步骤一 下载github上google开源的vr
        //1.1导入到我们的工作空间 common commonwidget panowidget
        //1.2添加到依赖
        //1.3依赖sdk中找不到的api
        //1.4准备一些测试素材,放置在assets目录下面,assets/a.jpg
        //1.5开启内存设置 android:largeHeap="true" 尽可能应使用最大内存
        //步骤二 将全景图片加载到内存中,再显示控件
        //2.1布局全景图片显示控件

        mPanoramaView = (VrPanoramaView) findViewById(R.id.vr_pano);
        //删除不需要的链接
        mPanoramaView.setInfoButtonEnabled(false);
        //隐藏全屏按钮
        mPanoramaView.setFullscreenButtonEnabled(false);

        //2.2所有的图片在内存中展示成Bitmap
        mImageTask = new ImageTask();

        mImageTask.execute();

    }

    private class ImageTask extends AsyncTask<Void,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                InputStream inputStream = getAssets().open("andes.jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            VrPanoramaView.Options option = new VrPanoramaView.Options();
            //立体图片,上半张显示在左眼,下半张显示在右眼
            option.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            VrPanoramaEventListener listener = new VrPanoramaEventListener(){
                @Override
                public void onLoadSuccess() {
                    super.onLoadSuccess();
                    //成功的情况下现在要进行全景图片的展示了

                    Toast.makeText(MainActivity.this, "请带上VR头盔", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoadError(String errorMessage) {
                    super.onLoadError(errorMessage);
                    //处理加载失败的情况
                    Toast.makeText(MainActivity.this, "E:"+errorMessage, Toast.LENGTH_SHORT).show();
                }
            };
            //2.5增加加载出错的业务逻辑处理
            mPanoramaView.setEventListener(listener);


            //2.4加载bitmap到控件上显示
            mPanoramaView.loadImageFromBitmap(bitmap,option);
        }
    }

    //步骤三,优化程序细节,页面退到后台，暂停显示,页面显示在屏幕上,恢复显示,销毁页面,释放全景图片资源


    //3.1页面退到后台,暂停显示
    @Override
    protected void onPause() {
        super.onPause();
        if(mPanoramaView!=null){
            mPanoramaView.pauseRendering();
        }
    }

    //3.2页面显示在屏幕,恢复显示
    @Override
    protected void onResume() {
        super.onResume();

        if(mPanoramaView!=null){
            mPanoramaView.resumeRendering();
        }
    }

    //3.3销毁页面,释放全景图片

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPanoramaView!=null){
            mPanoramaView.shutdown();
        }

        if(mImageTask!=null&&!mImageTask.isCancelled()){
            mImageTask.cancel(true);
            mImageTask=null;
        }
    }
}
