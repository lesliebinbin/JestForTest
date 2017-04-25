package firstdemo.as.tedu.cn.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import firstdemo.as.tedu.cn.myapplication.R;
import firstdemo.as.tedu.cn.myapplication.bean.MyUser;

/**
 * Created by pc on 2017/3/10.
 */

public class RegisterActivity extends Activity {
    @Bind(R.id.iv_regist_avatar)
    ImageView mIvRegistAvatar;
    @Bind(R.id.et_regist_username)
    EditText mEtRegistUsername;
    @Bind(R.id.et_regist_password)
    EditText mEtRegistPassword;
    @Bind(R.id.rb_regist_boy)
    RadioButton mRbRegistBoy;
    @Bind(R.id.rb_regist_girl)
    RadioButton mRbRegistGirl;
    @Bind(R.id.rg_gender)
    RadioGroup mRgGender;
    @Bind(R.id.btn_regist)
    Button mBtnRegist;
    private String mCameraPath;

    private String mAvartarUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }

    //头像点击事件
    @OnClick(R.id.iv_regist_avatar)
    public void setAvatar(View view){
        //1)创建一个隐式意图打开系统图库
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        //2)创建一个隐式intent打开系统的拍照程序
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //拍摄照片完成后,文件的存储路径
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),System.currentTimeMillis()+".jpg"
        );
        mCameraPath = file.getAbsolutePath();
        Uri value = Uri.fromFile(file);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT,value);

        //3)创建一个intent选择器
        Intent intent = Intent.createChooser(intent1,"选择头像...");
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{intent2});
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==100){
            final String filePath;//找到用户作为头像的图片在本地的地址
            if(data!=null){
                //用户是从图形库选择的头像
                Uri uri = data.getData();
                //根据uri找到图片在本地设备上的地址
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        null
                );

                cursor.moveToNext();

                filePath = cursor.getString(0);
                cursor.close();
            }else{
                filePath = mCameraPath;
            }

            //将filePath所代表的文件上传到Bmob服务器
            final BmobFile bf = new BmobFile(new File(filePath));
            bf.upload(this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    mAvartarUrl = bf.getFileUrl(RegisterActivity.this);
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    mIvRegistAvatar.setImageBitmap(bitmap);
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(RegisterActivity.this, "图片上传失败:"
                            +i+s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //注册按钮点击事件
    @OnClick(R.id.btn_regist)
    public void regist(View view){
        String username = mEtRegistUsername.getText().toString().trim();
        String password = mEtRegistPassword.getText().toString().trim();
        String sha = new String(Hex.encodeHex(DigestUtils.sha(password)));
        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String avatar = mAvartarUrl;
        boolean gender = true;
        if(mRbRegistGirl.isChecked()){
            gender = false;
        }
        MyUser user = new MyUser(avatar,username,gender,sha);
        user.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "恭喜你注册成功", Toast.LENGTH_SHORT).show();
                mIvRegistAvatar.setImageResource(R.drawable.ic_launcher);
                mAvartarUrl = null;
                mEtRegistPassword.setText("");
                mEtRegistUsername.setText("");
                mRgGender.check(R.id.rb_regist_boy);
            }

            @Override
            public void onFailure(int i, String s) {
                if(i==401){
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(RegisterActivity.this, "注册失败,原因:"+s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
