package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.MediaListBean;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class MediaListAdapter extends CommonRecyclerRecyclerAdapter<MediaListBean.MediaBean> {
    public MediaListAdapter(Context context, int layoutId, List<MediaListBean.MediaBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder,MediaListBean.MediaBean mediaBean, int position) {
        holder.setText(R.id.tv_title, mediaBean.title);
        holder.setText(R.id.tv_time, mediaBean.time);
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext,mediaBean.imageUrl,imageView,R.mipmap.no_image);
    }
}
