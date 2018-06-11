package aqtc.gl.school.main.home.activity;

import android.os.Bundle;
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
import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.main.home.adapter.NoticeListAdapter;
import aqtc.gl.school.main.home.bean.NoticeListBean;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class NoticeListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private NoticeListAdapter mNoticeListAdapter;
    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_common_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mNoticeListAdapter = new NoticeListAdapter(mContext,R.layout.item_noticelist, NoticeListBean.getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mNoticeListAdapter);
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
        mTitleView.setTitle(mContext.getResources().getString(R.string.home_notice));

    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }
}
