package leslie.binbin.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.SubjectInfo;
import leslie.binbin.cn.googleplay.http.HttpHelper;
import leslie.binbin.cn.googleplay.utils.BitmapHelper;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 *主题Holder
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private TextView tvTitle;
    private ImageView ivPic;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);
        ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        tvTitle.setText(data.des);
        mBitmapUtils.display(ivPic, HttpHelper.URL+"image?name="+data.url);
    }
}
