package aqtc.gl.school.main.home.adapter;

import android.content.Context;

import java.util.List;

import aqtc.gl.school.base.BaseMultiItemTypeAdapter;
import aqtc.gl.school.main.home.entity.HomeCommonListEntity;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class HomeCommonListAdapter extends BaseMultiItemTypeAdapter<HomeCommonListEntity.DataBean.ListBean> {
    public HomeCommonListAdapter(Context context, List<HomeCommonListEntity.DataBean.ListBean> datas) {
        super(context, datas);
        addItemViewDelegate(new PicItemDelagate(mContext));
        addItemViewDelegate(new TextItemDelagate(mContext));

    }
}
