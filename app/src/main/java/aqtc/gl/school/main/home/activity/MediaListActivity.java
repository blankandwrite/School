package aqtc.gl.school.main.home.activity;

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
import aqtc.gl.school.main.home.adapter.MediaListAdapter;
import aqtc.gl.school.main.home.model.MediaListEntity;
import aqtc.gl.school.utils.GsonUtil;
import aqtc.gl.school.utils.ToastUtils;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/10
 * @desc 师大媒体
 */
public class MediaListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private MediaListAdapter mMediaListAdapter;
    private List<MediaListEntity.DataBean.ListBean> mBeanList= new ArrayList();
    private int page=1;
    @Override
    public int getActivityViewById() {
        return R.layout.actitvity_common_list;
    }

    @Override
    public void initView() {
        mMediaListAdapter = new MediaListAdapter(mContext,R.layout.item_common_list,mBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mMediaListAdapter);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
               page = 1;
               getMediaList();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page = page +1;
                getMediaList();
            }
        });

        mMediaListAdapter.setOnItemClickListener(new MultiItemTypeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                MediaListEntity.DataBean.ListBean bean = mBeanList.get(position);
                MediaDetailActivity.goMediaDetailActivity(mContext,"媒体详情",String.valueOf(bean.id));
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
        mTitleView.setTitle(mContext.getResources().getString(R.string.home_media));
    }

    private void getMediaList(){
        Map<String,String> params = new HashMap<>();
        params.put("page",String.valueOf(page));
        OkHttpUtil.getInstance(mContext).doRequestByPost(CommonUrl.MEDIA_LIS, getTAG(), params,
                new OnResponse<String>() {
                    @Override
                    public void responseOk(String temp) {
                        MediaListEntity mediaListEntity = GsonUtil.jsonToBean(temp,MediaListEntity.class);
                     if (null != mediaListEntity && null != mediaListEntity.data && null != mediaListEntity.data.list){
                          if (page==1){
                              mSmartRefreshLayout.finishRefresh();
                              int rows = mediaListEntity.data.per_page;
                              if (mediaListEntity.data.list.size()>0){
                                        mMediaListAdapter.refresh(mediaListEntity.data.list);
                              }else {
                                  ToastUtils.showMsg(mContext,getString(R.string.no_data));
                              }
                          }else {
                              mSmartRefreshLayout.finishLoadMore();
                              if (mediaListEntity.data.list.size()>0){
                                  mMediaListAdapter.addAll(mediaListEntity.data.list);
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
