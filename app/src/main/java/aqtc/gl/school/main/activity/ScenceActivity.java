package aqtc.gl.school.main.activity;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;

/**
 * @author gl
 * @date 2018/5/7
 * @desc 校园风光
 */
public class ScenceActivity extends BaseActivity {

    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_scence;
    }

    @Override
    public void initView() {

    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
    }


}
