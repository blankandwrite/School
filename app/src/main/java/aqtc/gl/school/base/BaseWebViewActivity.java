package aqtc.gl.school.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import aqtc.gl.school.R;
import aqtc.gl.school.widget.ProgressWebView;
import aqtc.gl.school.widget.loadingview.FrameLayoutLoading;

/**
 * @author gl
 * @date 2018/5/16
 * @desc 只加载URL连接的WebView
 */
public class BaseWebViewActivity extends BaseActivity {
    protected ProgressWebView mWebView;
    protected FrameLayoutLoading mFrameLayoutLoading;
    protected String title;
    protected String url;

    public static void goBaseWebViewActivity(Context context,String url,String title){
        Intent intent = new Intent(context,BaseWebViewActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    @Override
    public int getActivityViewById() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mWebView = findViewById(R.id.webView);
        mFrameLayoutLoading = findViewById(R.id.loading_view);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        setView();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    public void setView() {
         mWebView.loadUrl(url);
    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(title);
    }
}
