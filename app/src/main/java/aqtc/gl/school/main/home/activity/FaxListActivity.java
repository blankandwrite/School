package aqtc.gl.school.main.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.main.home.adapter.FaxListAdapter;
import aqtc.gl.school.main.home.bean.FaxListBean;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class FaxListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private FaxListAdapter mFaxListAdapter;
    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_common_list;
    }

    @Override
    public void initView() {
        mFaxListAdapter = new FaxListAdapter(mContext,R.layout.item_common_list, FaxListBean.getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mFaxListAdapter);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mSmartRefreshLayout.finishRefresh(1000);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mSmartRefreshLayout.finishLoadMore(1000);
            }
        });


    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(mContext.getResources().getString(R.string.home_fax));

    }
}
