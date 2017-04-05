package leslie.binbin.cn.googleplay.ui.fragment;


import android.view.View;
import android.widget.TextView;

import leslie.binbin.cn.googleplay.ui.view.LoadingPage;
import leslie.binbin.cn.googleplay.utils.UIUtils;

public class GameFragment extends BaseFragment {

    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText("Game Fragment");
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
