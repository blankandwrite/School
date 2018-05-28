package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import aqtc.gl.school.R;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.home.entity.HomeCommonListEntity;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/15
 * @desc
 */
public class PicItemDelagate implements ItemViewDelegate<HomeCommonListEntity.DataBean.ListBean> {
    private Context mContext;

    public PicItemDelagate(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_common_list;
    }

    @Override
    public boolean isForViewType(HomeCommonListEntity.DataBean.ListBean item, int position) {
        if (!String.valueOf(item.category_id).equals(Global.SCIENCE_ID)){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public void convert(ViewHolder holder, HomeCommonListEntity.DataBean.ListBean listBean, int position) {
        holder.setText(R.id.tv_title, listBean.title);
        holder.setText(R.id.tv_time, "["+listBean.publish_time+"]");
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext ,listBean.imag_url,imageView,R.mipmap.no_image);
    }
}
