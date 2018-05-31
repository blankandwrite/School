package aqtc.gl.school.fragment;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.common.DataManager;
import aqtc.gl.school.fragment.adapter.AcademyAdpter;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 学院
 */
public class AcademyFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapse_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.appbar_layout_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;

    private AcademyAdpter mAcademyAdpter;
    public static AcademyFragment getInstance() {
        AcademyFragment academyFragment = new AcademyFragment();
        return academyFragment;
    }

    @Override
    public void initView(View rootView) {
        initTopView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAcademyAdpter = new AcademyAdpter(mContext,R.layout.item_academy, DataManager.academyList);
        mRecyclerView.setAdapter(mAcademyAdpter);

    }


    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_academy;
    }

    private void initTopView(){
        mToolbar.setTitleTextColor(Color.TRANSPARENT);
        mCollapsingToolbarLayout.setTitle("");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
               //     mTitle.setVisibility(View.VISIBLE);
                    mTitle.setVisibility(View.GONE);
                }else{
                    mTitle.setVisibility(View.GONE);

                }
            }
        });

    }
}
