package aqtc.gl.school.base;

import aqtc.gl.school.R;
import aqtc.gl.school.widget.ProgressWebView;

/**
 * @author gl
 * @date 2018/5/16
 * @desc
 */
public class BaseWebViewActivity extends BaseActivity {
    protected ProgressWebView mWebView;
    @Override
    public int getActivityViewById() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        mWebView = findViewById(R.id.webView);
        setView();
    }

    public void setView() {

    }


}
