package aqtc.gl.school.main.home.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeRecyclerAdapter;

import java.util.List;

import aqtc.gl.school.main.home.bean.NewsListBean;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class NewsListAdapter extends MultiItemTypeRecyclerAdapter<NewsListBean.NewsBean> {
    public NewsListAdapter(Context context, List<NewsListBean.NewsBean> datas) {
        super(context, datas);
        addItemViewDelegate(new PicItemDelagate(mContext));
        addItemViewDelegate(new TextItemDelagate(mContext));

    }
   /* public NewsListAdapter(Context context, int layoutId, List<NewsListBean.NewsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewsListBean.NewsBean newsBean, int position) {
        holder.setText(R.id.tv_title, newsBean.title);
        holder.setText(R.id.tv_time, newsBean.time);
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext,newsBean.imageUrl,imageView,R.mipmap.no_image);
    }*/
}
