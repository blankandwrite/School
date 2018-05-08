package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.ScenceBean;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/8
 * @desc
 */
public class ScenceAdapter extends CommonRecyclerRecyclerAdapter<ScenceBean.ScenceEntity> {

    public ScenceAdapter(Context context, int layoutId, List<ScenceBean.ScenceEntity> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ScenceBean.ScenceEntity item, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImageFit(mContext,item.url,imageView,R.mipmap.image_weibo_home);
        holder.setText(R.id.tv_scene,item.title);

    }
}
