package leslie.binbin.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.AppInfo;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 *首页的Holder已经
 */

public class HomeHolder extends BaseHolder<AppInfo> {

    private TextView mTvContent;

    @Override
    public View initView() {
        //1.加载布局
        View view = UIUtils.inflate(R.layout.list_item_home);
        //2.初始化控件
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        return view;

    }

    @Override
    public void refreshView(AppInfo data) {
        mTvContent.setText(data.name);
    }
}
