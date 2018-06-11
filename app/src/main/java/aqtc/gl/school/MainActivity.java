package aqtc.gl.school;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.fragment.AcademyFragment;
import aqtc.gl.school.fragment.EdumanagementFragment;
import aqtc.gl.school.fragment.FindFragment;
import aqtc.gl.school.fragment.HomeFragment;
import aqtc.gl.school.fragment.LeftMenuFragment;
import aqtc.gl.school.fragment.MyFragment;
import aqtc.gl.school.fragment.listener.OpenDrawerLayoutListener;
import aqtc.gl.school.utils.Utils;
import aqtc.gl.school.utils.apputil.Apputil;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 主页
 */
public class MainActivity extends BaseActivity implements OpenDrawerLayoutListener {
    @BindView(R.id.homeBtn)
    Button homeBtn;
    @BindView(R.id.academyBtn)
    Button academyBtn;
    @BindView(R.id.managementBtn)
    Button managementBtn;
    @BindView(R.id.findBtn)
    Button findBtn;
    @BindView(R.id.myBtn)
    Button myBtn;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fl_menu)
    FrameLayout leftMune;

    private Fragment[] fragments;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    private Button[] mTabs;


    @Override
    public int getActivityViewById() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initLeftMenu();
        initTabs();
        initContentFragment();

    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void initLeftMenu() {
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) leftMune.getLayoutParams();
        params.width = Utils.getScreenWidth(this) / 3 * 2;
        leftMune.setLayoutParams(params);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_menu, LeftMenuFragment.newInatace())
                .commit();
    }

    private void initTabs() {
        mTabs = new Button[5];
        mTabs[0] = homeBtn;
        mTabs[1] = academyBtn;
        mTabs[2] = findBtn;
        mTabs[3] = myBtn;
        mTabs[4] = managementBtn;
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }

    private void initContentFragment() {
        fragments = new Fragment[]{HomeFragment.getInstance(), AcademyFragment.getInstance(),
                FindFragment.getInstance(), MyFragment.getInstance(),
                EdumanagementFragment.getInstance()};
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragments[0])
                .show(fragments[0]).commit();

    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.homeBtn:
                index = 0;
                break;
            case R.id.academyBtn:
                index = 1;
                break;
            case R.id.findBtn:
                index = 2;
                break;
            case R.id.myBtn:
                index = 3;
                break;
            case R.id.managementBtn:
                index = 4;
                break;
        }

        if (index == 2 || index == 3) {
            if (!Apputil.checkLogin(MainActivity.this)) {
                return;
            }
        }

        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.content, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    @Override
    public void open() {
        if (!drawerLayout.isDrawerOpen(leftMune)) {
            drawerLayout.openDrawer(leftMune);
        }
    }

    @Override
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
