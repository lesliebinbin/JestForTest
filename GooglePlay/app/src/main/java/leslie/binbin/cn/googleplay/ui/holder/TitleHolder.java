package leslie.binbin.cn.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import leslie.binbin.cn.googleplay.R;
import leslie.binbin.cn.googleplay.domain.CategoryInfo;
import leslie.binbin.cn.googleplay.utils.UIUtils;

/**
 * Created by pc on 2017/4/6.
 */

/**
 * 分类模块的标题
 */

public class TitleHolder extends BaseHolder<CategoryInfo> {

    private TextView tvTitle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvTitle.setText(data.title);
    }
}
