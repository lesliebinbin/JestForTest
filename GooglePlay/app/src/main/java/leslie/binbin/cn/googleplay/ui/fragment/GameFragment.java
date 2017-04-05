package leslie.binbin.cn.googleplay.ui.fragment;


import android.view.View;

import leslie.binbin.cn.googleplay.ui.view.LoadingPage;

public class GameFragment extends BaseFragment {

    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_EMPTY;
    }
}
