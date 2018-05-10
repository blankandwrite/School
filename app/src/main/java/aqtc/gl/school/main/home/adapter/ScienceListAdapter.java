package aqtc.gl.school.main.home.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.ScienceListBean;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class ScienceListAdapter extends CommonRecyclerRecyclerAdapter<ScienceListBean.ScienceBean> {
    public ScienceListAdapter(Context context, int layoutId, List<ScienceListBean.ScienceBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ScienceListBean.ScienceBean scienceBean, int position) {
        holder.setText(R.id.tv_title, scienceBean.title);
        holder.setText(R.id.tv_time, scienceBean.time);
    }
}
