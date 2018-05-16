package aqtc.gl.school.main.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeRecyclerAdapter;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.common.ActivityWebView;
import aqtc.gl.school.main.home.adapter.NewsListAdapter;
import aqtc.gl.school.main.home.bean.NewsListBean;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/10
 * @desc 师大要闻列表
 */
public class NewsListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private NewsListAdapter mNewsListAdapter;

    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_common_list;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
     //   mNewsListAdapter = new NewsListAdapter(mContext,R.layout.item_common_list, NewsListBean.getData());
        mNewsListAdapter = new NewsListAdapter(mContext, NewsListBean.getData());
        mRecyclerView.setAdapter(mNewsListAdapter);
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

        mNewsListAdapter.setOnItemClickListener(new MultiItemTypeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ActivityWebView.goActivityWebView(mContext,"file:///android_asset/index.html",ActivityWebView.TYPE_URL,"详情");
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(mContext.getResources().getString(R.string.home_news));
    }
}
