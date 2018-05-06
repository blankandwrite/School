package aqtc.gl.school.fragment;

import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 教务管理
 */
public class EdumanagementFragment extends BaseFragment {

    public static EdumanagementFragment getInstance() {
        EdumanagementFragment edumanagementFragment = new EdumanagementFragment();
        return edumanagementFragment;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_edumanagemetn;
    }
}
