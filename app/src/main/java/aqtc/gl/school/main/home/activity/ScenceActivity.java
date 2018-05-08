package aqtc.gl.school.main.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeRecyclerAdapter;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.main.home.adapter.ScenceAdapter;
import aqtc.gl.school.main.home.bean.ScenceBean;
import aqtc.gl.school.utils.ToastUtils;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/7
 * @desc 校园风光
 */
public class ScenceActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private ScenceAdapter mScenceAdapter;
    private List<ScenceBean.ScenceEntity> mScenceEntityList;
    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_scence;
    }

    @Override
    public void initView() {
        mScenceEntityList= ScenceBean.getData();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        mScenceAdapter = new ScenceAdapter(mContext,R.layout.item_scence, mScenceEntityList);
        mRecyclerView.setAdapter(mScenceAdapter);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

        mScenceAdapter.setOnItemClickListener(new MultiItemTypeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ToastUtils.showMsg(mContext,mScenceEntityList.get(position).title);
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
    }


}
