package aqtc.gl.school.fragment;

import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.widget.ProgressWebView;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 教务管理
 */
public class EdumanagementFragment extends BaseFragment {
    @BindView(R.id.webView)
    ProgressWebView mProgressWebView;

  /*  @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;*/

    public static EdumanagementFragment getInstance() {
        EdumanagementFragment edumanagementFragment = new EdumanagementFragment();
        return edumanagementFragment;
    }

    @Override
    public void initView(View rootView) {
        addStatusBarHeight();
        mProgressWebView.loadUrl("http://210.45.175.14/");
     /*   mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mSmartRefreshLayout.finishRefresh(1000);
            }
        });*/
    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_edumanagemetn;
    }
}
