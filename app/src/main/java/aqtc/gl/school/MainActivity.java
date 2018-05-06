package aqtc.gl.school;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import aqtc.gl.school.fragment.AcademyFragment;
import aqtc.gl.school.fragment.EdumanagementFragment;
import aqtc.gl.school.fragment.FindFragment;
import aqtc.gl.school.fragment.HomeFragment;
import aqtc.gl.school.fragment.listener.OpenDrawerLayoutListener;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements OpenDrawerLayoutListener {
    @BindView(R.id.homeBtn)
    Button homeBtn;
    @BindView(R.id.academyBtn)
    Button academyBtn;
    @BindView(R.id.managementBtn)
    Button managementBtn;
    @BindView(R.id.findBtn)
    Button findBtn;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigation;

    private Fragment[] fragments;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;
    private Button[] mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTabs();
        initContentFragment();

    }

    private void initTabs() {
        mTabs = new Button[4];
        mTabs[0] = homeBtn;
        mTabs[1] = academyBtn;
        mTabs[2] = findBtn;
        mTabs[3] = managementBtn;
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }

    private void initContentFragment() {
        fragments = new Fragment[]{HomeFragment.getInstance(), AcademyFragment.getInstance(),
                FindFragment.getInstance(), EdumanagementFragment.getInstance()};
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
            case R.id.managementBtn:
                index = 3;
                break;
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
        drawerLayout.openDrawer(navigation);
    }
}
