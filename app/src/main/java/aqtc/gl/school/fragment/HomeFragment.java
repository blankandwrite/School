package aqtc.gl.school.fragment;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.transform.ZoomOutSlideTransformer;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.fragment.banner.BannerBean;
import aqtc.gl.school.fragment.banner.SimpleImageBanner;
import aqtc.gl.school.fragment.listener.OpenDrawerLayoutListener;
import aqtc.gl.school.main.activity.ScenceActivity;
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
    void openDraw(){
        if (null !=mOpenDrawerLayoutListener ){
            mOpenDrawerLayoutListener.open();
        }
    }

    @OnClick(R.id.tv_scene)
    void goScene(){
       startActivity(new Intent(mContext, ScenceActivity.class));
    }
}
