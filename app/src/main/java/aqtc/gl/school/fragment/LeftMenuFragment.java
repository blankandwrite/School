package aqtc.gl.school.fragment;

import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;

/**
 * @author gl
 * @date 2018/5/13
 * @desc
 */
public class LeftMenuFragment extends BaseFragment {

    public static LeftMenuFragment newInatace(){
        return new LeftMenuFragment();
    }
    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_menu_left;
    }
}
