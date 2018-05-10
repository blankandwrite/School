package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.FaxListBean;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class FaxListAdapter extends CommonRecyclerRecyclerAdapter<FaxListBean.FaxBean> {
    public FaxListAdapter(Context context, int layoutId, List<FaxListBean.FaxBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FaxListBean.FaxBean faxBean, int position) {
        holder.setText(R.id.tv_title, faxBean.title);
        holder.setText(R.id.tv_time, faxBean.time);
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext,faxBean.imageUrl,imageView,R.mipmap.no_image);
    }
}
