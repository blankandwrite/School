package aqtc.gl.school.fragment;


import android.content.Context;
import android.view.View;

import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.transform.ZoomOutSlideTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.common.preload.Preloader;
import aqtc.gl.school.common.preload.PreloaderCallback;
import aqtc.gl.school.fragment.banner.BannerBean;
import aqtc.gl.school.fragment.banner.SimpleImageBanner;
import aqtc.gl.school.fragment.entity.CategoryBean;
import aqtc.gl.school.fragment.listener.OpenDrawerLayoutListener;
import aqtc.gl.school.main.home.activity.HomeCommonListActivity;
import aqtc.gl.school.main.home.activity.NoticeListActivity;
import aqtc.gl.school.main.home.activity.ScenceActivity;
import aqtc.gl.school.utils.GsonUtil;
import aqtc.gl.school.utils.ToastUtils;
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
        getCategory();

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
        HomeCommonListActivity.goHomeCommonListActivity(mContext, Global.NEWS_ID,getString(R.string.home_news));
    }

    @OnClick(R.id.tv_fax)
    void goFax() {
        HomeCommonListActivity.goHomeCommonListActivity(mContext, Global.FAX_ID,getString(R.string.home_fax));
    }

    @OnClick(R.id.tv_media)
    void goMedia() {
        HomeCommonListActivity.goHomeCommonListActivity(mContext, Global.MEDIA_ID,getString(R.string.home_media));
    }
    @OnClick(R.id.tv_notice)
    void goNotice() {
        Apputil.goActivity(mContext, NoticeListActivity.class);
    }
    @OnClick(R.id.tv_science)
    void goScience() {
        HomeCommonListActivity.goHomeCommonListActivity(mContext, Global.SCIENCE_ID,getString(R.string.home_science));
    }

    private void getCategory(){
        Map<String,String> params = new HashMap<>();
        params.put("school_id","1");
        Preloader.getInstance(mContext).loadData(mContext, CommonUrl.ARTICLE_CATEGORY, getTAG(), params,
                new PreloaderCallback<CategoryBean.DataBean>() {
                    @Override
                    public List<CategoryBean.DataBean> parseResult(String json) {
                        mCategoryBean = GsonUtil.jsonToBean(json,CategoryBean.class);
                        return mCategoryBean.data;
                    }

                    @Override
                    public void getResult(List<CategoryBean.DataBean> result) {

                    }

                    @Override
                    public void fail(String errorMsg) {
                        ToastUtils.showMsg(mContext,errorMsg);
                    }
                });

    }


}
