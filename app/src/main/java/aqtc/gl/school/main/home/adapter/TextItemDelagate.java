package aqtc.gl.school.main.home.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import aqtc.gl.school.R;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.home.model.HomeCommonListEntity;

/**
 * @author gl
 * @date 2018/5/15
 * @desc
 */
public class TextItemDelagate implements ItemViewDelegate<HomeCommonListEntity.DataBean.ListBean> {
    private Context mContext;

    public TextItemDelagate(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_science_list;
    }

    @Override
    public boolean isForViewType(HomeCommonListEntity.DataBean.ListBean item, int position) {
        if (String.valueOf(item.category_id).equals(Global.SCIENCE_ID)){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public void convert(ViewHolder holder, HomeCommonListEntity.DataBean.ListBean listBean, int position) {
        holder.setText(R.id.tv_title, listBean.title);
        holder.setText(R.id.tv_time, "["+listBean.publish_time+"]");
    }
}
