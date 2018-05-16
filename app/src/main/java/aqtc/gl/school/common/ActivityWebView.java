package aqtc.gl.school.common;

import android.content.Context;
import android.content.Intent;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.widget.ProgressWebView;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/16
 * @desc
 */
public class ActivityWebView extends BaseActivity {
    @BindView(R.id.webView)
    ProgressWebView mWebView;

    public static final String TYPE_URL="url";
    public  static final String TYPE_CONTENT="content";

    private String type;
    private String content_url;
    private String title;
    public static void goActivityWebView(Context context,String content_url,String type,String title){
        Intent intent = new Intent(context,ActivityWebView.class);
        intent.putExtra("content_url",content_url);
        intent.putExtra("type",type);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    @Override
    public int getActivityViewById() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        content_url = intent.getStringExtra("content_url");
        title = intent.getStringExtra("title");
        if (type.equals(TYPE_URL)){
            mWebView.loadUrl(content_url);
        }else {
            //webview.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
            mWebView.loadData(content_url,"text/html","UTF-8");
        }

    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(title);
    }
}
