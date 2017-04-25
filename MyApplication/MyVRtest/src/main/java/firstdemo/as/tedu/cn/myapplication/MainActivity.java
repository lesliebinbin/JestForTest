package firstdemo.as.tedu.cn.myapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    LoadPanoramaImageTask mLoadPanoramaImageTask;
    @Bind(R.id.vr_panoramaview)
    VrPanoramaView mVrPanoramaview;

    @OnClick(R.id.button2)
    public void seeMovie(View view){
        Intent intent = new Intent(this,VRMovieActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mVrPanoramaview.setEventListener(new VrPanoramaEventListener(){
            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                Toast.makeText(MainActivity.this, "加载成功了,主淫", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Toast.makeText(MainActivity.this, "加载失败了,主任", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick() {
                super.onClick();
                Toast.makeText(MainActivity.this, "干嘛点我呀,白痴", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDisplayModeChanged(int newDisplayMode) {
                super.onDisplayModeChanged(newDisplayMode);
                Toast.makeText(MainActivity.this, "换来换去很好玩么?", Toast.LENGTH_SHORT).show();
            }
        });

        mLoadPanoramaImageTask = new LoadPanoramaImageTask();
        mLoadPanoramaImageTask.execute();

    }

    @Override
    protected void onPause() {
        mVrPanoramaview.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrPanoramaview.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        mVrPanoramaview.shutdown();
        // The background task has a 5 second timeout so it can potentially stay alive for 5 seconds
        // after the activity is destroyed unless it is explicitly cancelled.
        if (mLoadPanoramaImageTask != null) {
            mLoadPanoramaImageTask.cancel(true);
        }
        super.onDestroy();
    }


    private class LoadPanoramaImageTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                //加载assets目录下的全景图片
                AssetManager assetManager = getAssets();
                InputStream open = assetManager.open("panoramas/testRoom1_2kStereo.jpg");
                return BitmapFactory.decodeStream(open);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("出异常了,大哥");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            VrPanoramaView.Options options = new VrPanoramaView.Options();
            //图片为立体图片
            options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
            mVrPanoramaview.loadImageFromBitmap(bitmap, options);
        }
    }
}
