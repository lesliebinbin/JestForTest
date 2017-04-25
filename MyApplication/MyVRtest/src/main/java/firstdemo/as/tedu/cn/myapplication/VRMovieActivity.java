package firstdemo.as.tedu.cn.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VRMovieActivity extends AppCompatActivity {

    @Bind(R.id.vr_vidie_view)
    VrVideoView mVrVedioView;

    boolean isPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrmovie);
        ButterKnife.bind(this);

        VrVideoView.Options options = new VrVideoView.Options();

//视频类型为立体视频
        options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;

        try {
            mVrVedioView.loadVideoFromAsset("videos/testRoom1_1920Mono.mp4", options);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Prevent the view from rendering continuously when in the background.
        mVrVedioView.pauseRendering();
        // If the video is playing when onPause() is called, the default behavior will be to pause
        // the video and keep it paused when onResume() is called.
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrVedioView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        mVrVedioView.shutdown();
        super.onDestroy();
    }
}
