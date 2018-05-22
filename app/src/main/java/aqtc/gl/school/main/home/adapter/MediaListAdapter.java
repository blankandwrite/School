package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.base.AdapterBase;
import aqtc.gl.school.main.home.model.MediaListEntity;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class MediaListAdapter extends AdapterBase<MediaListEntity.DataBean.ListBean> {

    public MediaListAdapter(Context context, int layoutId, List<MediaListEntity.DataBean.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MediaListEntity.DataBean.ListBean listBean, int position) {
        holder.setText(R.id.tv_title, listBean.title);
        holder.setText(R.id.tv_time, listBean.publish_time);
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext,"",imageView,R.mipmap.no_image);
    }
}
