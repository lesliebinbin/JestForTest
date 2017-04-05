package leslie.binbin.cn.okhttpdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView mTvResult;
    //拿到okHttpClient对象
    private static String mBaseUrl = "http://192.168.57.2/structdemo01/";

    OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvResult = (TextView) findViewById(R.id.tv_result);
    }

    public void doGet(View view){

        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(mBaseUrl+"login?username=leslie" +
                "&password=woainvren1").build();
        executeRequest(request);


    }

    private void executeRequest(Request request) {
        //将Request封装为Call
        Call call = okHttpClient.newCall(request);


        //执行call
        //Response response = call.execute();

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Leslie.e("onFailure:"+e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                final String result = response.body().string();
                Leslie.e("onResponse:"+ result);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText(Html.fromHtml(result));
                    }
                });
            }
        });
    }

    public void doPost(View view){
        Request.Builder builder = new Request.Builder();
        FormEncodingBuilder requestBodyBuilder = new FormEncodingBuilder();
        RequestBody requestBody = requestBodyBuilder
                .add("username", "黄志斌")
                .add("password", "woainvren5").build();
        Request request = builder.url(mBaseUrl + "login").post(requestBody).build();
        executeRequest(request);
    }

    public void doPostString(View view){
        Request.Builder builder = new Request.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8")
                , "{username:开房,password:Room1201}");
        Request request = builder.url(mBaseUrl+"postString").post(requestBody)
                .build();
        executeRequest(request);
    }

    public void doPostFile(View view){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC
        ),"leslie.txt");
        if(!file.exists()){
            Leslie.e(file.getAbsolutePath()+" does not exist!");
            Toast.makeText(this, "文件路径不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Request.Builder builder = new Request.Builder();
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/octet-stream")
                        ,file);
        Request request = builder.url(mBaseUrl+"postFile")
                .post(requestBody).build();
        executeRequest(request);
    }

    public void doUpload(View view){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC
        ),"leslie.txt");
        if(!file.exists()){
            Leslie.e(file.getAbsolutePath()+" does not exist!");
            Toast.makeText(this, "文件路径不存在", Toast.LENGTH_SHORT).show();
            return;
        }


        MultipartBuilder multiBuilder = new MultipartBuilder();
        multiBuilder.type(MultipartBuilder.FORM)
                .addFormDataPart("username","黄志斌")
                .addFormDataPart("password","woainvren1")
                .addFormDataPart("mPhoto","binbin.txt"
                        ,RequestBody.create(MediaType.parse("application/octet-stream")
                                ,file));
        RequestBody body = multiBuilder.build();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(mBaseUrl+"uploadinfo")
                .post(body).build();
        executeRequest(request);
    }
}
