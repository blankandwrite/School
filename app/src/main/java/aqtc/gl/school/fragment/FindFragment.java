package aqtc.gl.school.fragment;

import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 发现
 */
public class FindFragment extends BaseFragment {

    public static FindFragment getInstance() {
        FindFragment findFragment = new FindFragment();
        return findFragment;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_find;
    }
}
