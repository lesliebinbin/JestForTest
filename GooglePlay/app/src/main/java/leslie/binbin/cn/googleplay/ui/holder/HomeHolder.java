package leslie.binbin.cn.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.http.HttpHelper;
import leslie.binbin.cn.googleplay.utils.BitmapHelper;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 *首页的Holder已经
 */

public class HomeHolder extends BaseHolder<AppInfo> {

    private TextView mTvContent;
    private TextView tvName;
    private TextView tvSize;
    private TextView tvDes;
    private ImageView ivIcon;
    private RatingBar rbStar;

    private BitmapUtils mBitmapUtils;

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
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;


    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        tvDes.setText(data.des);
        rbStar.setRating(data.stars);
        mBitmapUtils.display(ivIcon, HttpHelper.URL+"image?name="+data.iconUrl );
    }
}
