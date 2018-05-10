package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.NewsListBean;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class NewsListAdapter extends CommonRecyclerRecyclerAdapter<NewsListBean.NewsBean> {
    public NewsListAdapter(Context context, int layoutId, List<NewsListBean.NewsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NewsListBean.NewsBean newsBean, int position) {
        holder.setText(R.id.tv_title, newsBean.title);
        holder.setText(R.id.tv_time, newsBean.time);
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext,newsBean.imageUrl,imageView,R.mipmap.no_image);
    }
}
