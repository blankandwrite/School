package aqtc.gl.school.fragment;


import android.content.Context;
import android.view.View;

import com.android.okhttpwrapper.OkHttpUtil;
import com.android.okhttpwrapper.callback.OnResponse;
import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.transform.ZoomOutSlideTransformer;

import java.util.HashMap;
import java.util.Map;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.fragment.banner.BannerBean;
import aqtc.gl.school.fragment.banner.SimpleImageBanner;
import aqtc.gl.school.fragment.entity.CategoryBean;
import aqtc.gl.school.fragment.listener.OpenDrawerLayoutListener;
import aqtc.gl.school.main.home.activity.FaxListActivity;
import aqtc.gl.school.main.home.activity.MediaListActivity;
import aqtc.gl.school.main.home.activity.NewsListActivity;
import aqtc.gl.school.main.home.activity.NoticeListActivity;
import aqtc.gl.school.main.home.activity.ScenceActivity;
import aqtc.gl.school.main.home.activity.ScienceListActivity;
import aqtc.gl.school.utils.GsonUtil;
import aqtc.gl.school.utils.apputil.Apputil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    SimpleImageBanner mImageBanner;

    private OpenDrawerLayoutListener mOpenDrawerLayoutListener;
    private CategoryBean mCategoryBean;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mOpenDrawerLayoutListener = (OpenDrawerLayoutListener) context;
    }

    @Override
    public void initView(View rootView) {
        mImageBanner
                .setSelectAnimClass(ZoomInEnter.class)
                .setSource(BannerBean.getBannerBean().data)
                .setTransformerClass(ZoomOutSlideTransformer.class)
                .startScroll();


    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_home;
    }

    @OnClick(R.id.iv_top)
    void openDraw() {
        if (null != mOpenDrawerLayoutListener) {
            mOpenDrawerLayoutListener.open();
        }
    }

    @OnClick(R.id.tv_scene)
    void goScene() {
        Apputil.goActivity(mContext, ScenceActivity.class);
    }

    @OnClick(R.id.tv_news)
    void goNews() {
        Apputil.goActivity(mContext, NewsListActivity.class);
    }

    @OnClick(R.id.tv_fax)
    void goFax() {
        Apputil.goActivity(mContext, FaxListActivity.class);
    }

    @OnClick(R.id.tv_media)
    void goMedia() {
        Apputil.goActivity(mContext, MediaListActivity.class);
    }
    @OnClick(R.id.tv_notice)
    void goNotice() {
        Apputil.goActivity(mContext, NoticeListActivity.class);
    }
    @OnClick(R.id.tv_science)
    void goScience() {
        Apputil.goActivity(mContext, ScienceListActivity.class);
    }

    private void getIndexdata(){
        Map<String,String> params = new HashMap<>();
        params.put("school_id","1");
        OkHttpUtil.getInstance(mContext).doRequestByPost(CommonUrl.ARTICLE_CATEGORY, getTAG(), params,
                new OnResponse<String>() {
                    @Override
                    public void responseOk(String temp) {
                        mCategoryBean = GsonUtil.jsonToBean(temp,CategoryBean.class);
                        if (null != mCategoryBean && null != mCategoryBean.data){
                            
                        }
                    }

                    @Override
                    public void responseFail(String msg) {

                    }
                });
    }


}
