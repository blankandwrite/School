package aqtc.gl.school.fragment;

import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 学院
 */
public class AcademyFragment extends BaseFragment {

    public static AcademyFragment getInstance() {
        AcademyFragment academyFragment = new AcademyFragment();
        return academyFragment;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_academy;
    }
}
