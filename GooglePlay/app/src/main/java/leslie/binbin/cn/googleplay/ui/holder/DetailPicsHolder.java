package leslie.binbin.cn.googleplay.ui.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.HttpHelper;
import leslie.binbin.cn.googleplay.utils.BitmapHelper;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * 应用详情页-截图
 */

public class DetailPicsHolder extends BaseHolder<AppInfo> {

    private ImageView[] ivPics;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        ivPics = new ImageView[5];
        ivPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        ivPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        ivPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        ivPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        ivPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<String> screen = data.screen;

        for(int i=0;i<5;i++){
            if(i<screen.size()){
                mBitmapUtils.display(ivPics[i], HttpHelper.URL+"image?name="+screen.get(i));
                ivPics[i].setOnClickListener((v->{
                    //跳转Activity展示ViewPager
                    //将集合通过intent传递过去为当前点击位置的i也成传递过去

                    Intent intent  = new Intent();
                    intent.putExtra("list",screen);
                }));
            }else{
                ivPics[i].setVisibility(View.GONE);
            }
        }
    }
}
