package aqtc.gl.school.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import aqtc.gl.school.main.home.presenter.homecommondetail.HomeCommomDatailPresenter;
import aqtc.gl.school.main.home.presenter.homecommondetail.HomeCommonDetailContract;
import aqtc.gl.school.widget.ProgressWebView;
import aqtc.gl.school.widget.loadingview.FrameLayoutLoading;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/22
 * @desc
 */
public class HomeCommonDetailActivity extends BaseActivity<HomeCommomDatailPresenter>
        implements HomeCommonDetailContract.HomeCommonDetailView {
    @BindView(R.id.webView)
    ProgressWebView mWebView;
    @BindView(R.id.loading_view)
    FrameLayoutLoading mFrameLayoutLoading;

    protected String title;
    protected String id;

    public static void goHomeCommonDetailActivity(Context context, String title, String id) {
        Intent intent = new Intent(context, HomeCommonDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getActivityViewById() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");

    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(title);

    }

    @Override
    public void handleData() {
        mPresenter.getData(mContext,getTAG(),id);
        mFrameLayoutLoading.setRefreashClickListener(new FrameLayoutLoading.RefreashClickListener() {
            @Override
            public void setRefresh() {
                mPresenter.getData(mContext,getTAG(),id);
            }
        });
    }

    @Override
    protected HomeCommomDatailPresenter getPresenter() {
        return new HomeCommomDatailPresenter(this);
    }
    
    @Override
    public void showViewLoading() {
        mFrameLayoutLoading.showErrorView();
    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onSucess(HomeCommonDetailEntity.DataBean dataBean) {
        mFrameLayoutLoading.hideView();
        mWebView.loadDataWithBaseURL("", dataBean.html_content, "text/html", "UTF-8", "");
    }

    @Override
    public void onFail(String err) {
       showViewLoading();
    }
}
