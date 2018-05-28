package aqtc.gl.school.main.home.activity;

import android.content.Context;
import android.content.Intent;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.main.home.activity.mvp.homecommondetail.HomeCommomDatailPresenter;
import aqtc.gl.school.main.home.activity.mvp.homecommondetail.HomeCommonDetailContract;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import aqtc.gl.school.utils.ToastUtils;
import aqtc.gl.school.widget.ProgressWebView;
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
    public void initView() {
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mPresenter.getData(mContext,getTAG(),id);
    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(title);

    }

    @Override
    protected HomeCommomDatailPresenter getPresenter() {
        return new HomeCommomDatailPresenter(this);
    }
    
    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onSucess(HomeCommonDetailEntity.DataBean dataBean) {
        mWebView.loadDataWithBaseURL("", dataBean.html_content, "text/html", "UTF-8", "");
    }

    @Override
    public void onFail(String err) {
        ToastUtils.showMsg(mContext, err);
    }
}
