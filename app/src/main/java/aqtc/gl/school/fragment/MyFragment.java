package aqtc.gl.school.fragment;

import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.main.home.activity.MyInfoEditActivity;
import butterknife.OnClick;

/**
 * @author gl
 * @date 2018/5/26
 * @desc
 */
public class MyFragment extends BaseFragment {


    public static MyFragment getInstance(){
        return new MyFragment();
    }
    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_my;
    }
    @OnClick(R.id.tv_name)
    void name(){
        MyInfoEditActivity.goMyInfoEditActivity(mContext,0);
    }
    @OnClick(R.id.tv_academy)
    void academy(){
        MyInfoEditActivity.goMyInfoEditActivity(mContext,1);
    }
    @OnClick(R.id.tv_jibie)
    void jibie(){
        MyInfoEditActivity.goMyInfoEditActivity(mContext,2);
    }
    @OnClick(R.id.tv_zhuanye)
    void zhuanye(){
        MyInfoEditActivity.goMyInfoEditActivity(mContext,3);
    }
    @OnClick(R.id.tv_class)
    void clzz(){
        MyInfoEditActivity.goMyInfoEditActivity(mContext,4);
    }

}
