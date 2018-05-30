package aqtc.gl.school.main.home.activity;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.home.adapter.HomeCommonListAdapter;
import aqtc.gl.school.main.home.entity.HomeCommonListEntity;
import aqtc.gl.school.main.home.presenter.homecommonList.HomeCommonListContract;
import aqtc.gl.school.main.home.presenter.homecommonList.HomeCommonListPresenter;
import aqtc.gl.school.utils.ToastUtils;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/10
 * @desc 师大要闻、 师大媒体、校园传真、学术动态
 */
public class HomeCommonListActivity extends BaseActivity<HomeCommonListPresenter> implements
        HomeCommonListContract.IomeCommonListView{
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private HomeCommonListAdapter mCommonListAdapter;
    private List<HomeCommonListEntity.DataBean.ListBean> mBeanList= new ArrayList();
    private int page=1;
    private String categoryId;
    private String title;
    private String detailTitle;

    public static void goHomeCommonListActivity(Context context,String categoryId,String title){
        Intent intent = new Intent(context,HomeCommonListActivity.class);
        intent.putExtra("categoryId",categoryId);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_common_list;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryId");
        title = intent.getStringExtra("title");
        if (Global.NEWS_ID.equals(categoryId)){
            detailTitle = "新闻详情";
        }else if (Global.FAX_ID.equals(categoryId)){
            detailTitle = "传真详情";
        }else if (Global.MEDIA_ID.equals(categoryId)){
            detailTitle = "媒体详情";
        }else {
            detailTitle = "学术详情";
        }
        mCommonListAdapter = new HomeCommonListAdapter(mContext,mBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mCommonListAdapter);
        mSmartRefreshLayout.autoRefresh();

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
               page = 1;
                mPresenter.getListData(mContext,getTAG(), Global.SCHOOL_ID,page,categoryId,Global.ROWS);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page = page +1;
                mPresenter.getListData(mContext,getTAG(), Global.SCHOOL_ID,page,categoryId,Global.ROWS);

            }
        });

        mCommonListAdapter.setOnItemClickListener(new MultiItemTypeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                HomeCommonListEntity.DataBean.ListBean bean = mBeanList.get(position);
                HomeCommonDetailActivity.goHomeCommonDetailActivity(mContext,detailTitle,String.valueOf(bean.id));
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
        mTitleView.setTitle(title);
    }

    @Override
    protected HomeCommonListPresenter getPresenter() {
        return new HomeCommonListPresenter(this);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }


    @Override
    public void onScuess(List<HomeCommonListEntity.DataBean.ListBean> listBeans) {
        if (page==1){
            mSmartRefreshLayout.finishRefresh();
            if (listBeans.size()>0){
                mCommonListAdapter.refresh(listBeans);
            }else {
                ToastUtils.showMsg(mContext,getString(R.string.no_data));
            }
        }else {
            mSmartRefreshLayout.finishLoadMore();
            if (listBeans.size()>0){
                mCommonListAdapter.addAll(listBeans);
            }else {
                ToastUtils.showMsg(mContext,getString(R.string.no_more_data));
            }
        }
    }

    @Override
    public void onFail(String err) {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
        ToastUtils.showMsg(mContext,err);
    }
}
