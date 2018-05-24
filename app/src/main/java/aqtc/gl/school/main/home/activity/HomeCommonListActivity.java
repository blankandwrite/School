package aqtc.gl.school.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.okhttpwrapper.OkHttpUtil;
import com.android.okhttpwrapper.callback.OnResponse;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.home.adapter.HomeCommonListAdapter;
import aqtc.gl.school.main.home.model.HomeCommonListEntity;
import aqtc.gl.school.utils.GsonUtil;
import aqtc.gl.school.utils.ToastUtils;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/10
 * @desc 师大要闻、 师大媒体、校园传真、学术动态
 */
public class HomeCommonListActivity extends BaseActivity {
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
                getList();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page = page +1;
                getList();
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

    private void getList(){
        Map<String,String> params = new HashMap<>();
        params.put("school_id", Global.SCHOOL_ID);
        params.put("page",String.valueOf(page));
        params.put("category_id",categoryId);
        params.put("limit",Global.ROWS);
        OkHttpUtil.getInstance(mContext).doRequestByPost(CommonUrl.ARTICLE__LIS, getTAG(), params,
                new OnResponse<String>() {
                    @Override
                    public void responseOk(String temp) {
                        HomeCommonListEntity homeCommonListEntity = GsonUtil.jsonToBean(temp,HomeCommonListEntity.class);
                     if (null != homeCommonListEntity && null != homeCommonListEntity.data && null != homeCommonListEntity.data.list){
                          if (page==1){
                              mSmartRefreshLayout.finishRefresh();
                              if (homeCommonListEntity.data.list.size()>0){
                                  mCommonListAdapter.refresh(homeCommonListEntity.data.list);
                              }else {
                                  ToastUtils.showMsg(mContext,getString(R.string.no_data));
                              }
                          }else {
                              mSmartRefreshLayout.finishLoadMore();
                              if (homeCommonListEntity.data.list.size()>0){
                                  mCommonListAdapter.addAll(homeCommonListEntity.data.list);
                              }else {
                                  ToastUtils.showMsg(mContext,getString(R.string.no_more_data));
                              }
                          }
                      }
                    }

                    @Override
                    public void responseFail(String msg) {
                        mSmartRefreshLayout.finishRefresh();
                        ToastUtils.showMsg(mContext,getString(R.string.no_data));
                    }
                });
    }
}
