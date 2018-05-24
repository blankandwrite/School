package aqtc.gl.school.main.home.activity;

import android.content.Context;
import android.content.Intent;

import com.android.okhttpwrapper.OkHttpUtil;
import com.android.okhttpwrapper.callback.OnResponse;

import java.util.HashMap;
import java.util.Map;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseWebViewActivity;
import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.main.home.model.HomeCommonDetailEntity;
import aqtc.gl.school.utils.GsonUtil;
import aqtc.gl.school.utils.ToastUtils;

/**
 * @author gl
 * @date 2018/5/22
 * @desc
 */
public class HomeCommonDetailActivity extends BaseWebViewActivity {
    protected String title;
    protected String id;

    public static void goHomeCommonDetailActivity(Context context, String title, String id) {
        Intent intent = new Intent(context, HomeCommonDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
    @Override
    public void setView() {
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        getMediaDetail();
    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(title);
    }

    private void getMediaDetail(){
        Map<String,String> params = new HashMap<>();
        params.put("id",id);
        OkHttpUtil.getInstance(mContext).doRequestByPost(CommonUrl.ARTICLE__DETAIL, getTAG(), params,
                new OnResponse<String>() {
                    @Override
                    public void responseOk(String temp) {
                        HomeCommonDetailEntity homeCommonDetailEntity = GsonUtil.jsonToBean(temp,HomeCommonDetailEntity.class);
                        if (null != homeCommonDetailEntity && null != homeCommonDetailEntity.data){
                            String html = homeCommonDetailEntity.data.html_content;
                            mWebView.loadDataWithBaseURL("", homeCommonDetailEntity.data.html_content,"text/html","UTF-8","");
                        }
                    }

                    @Override
                    public void responseFail(String msg) {
                        ToastUtils.showMsg(mContext,msg);
                    }
                });
    }
}
