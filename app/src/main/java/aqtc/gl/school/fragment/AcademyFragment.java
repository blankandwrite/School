package aqtc.gl.school.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    private AcademyAdpter mAcademyAdpter;
    public static AcademyFragment getInstance() {
        AcademyFragment academyFragment = new AcademyFragment();
        return academyFragment;
    }

    @Override
    public void initView(View rootView) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAcademyAdpter = new AcademyAdpter(mContext,R.layout.item_academy, DataManager.academyList);
        mRecyclerView.setAdapter(mAcademyAdpter);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mSmartRefreshLayout.finishRefresh(2000);
            }
        });

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_academy;
    }
}
