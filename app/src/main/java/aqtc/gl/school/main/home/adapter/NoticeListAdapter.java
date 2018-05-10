package aqtc.gl.school.main.home.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.NoticeListBean;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class NoticeListAdapter extends CommonRecyclerRecyclerAdapter<NoticeListBean.NoticeBean> {
    public NoticeListAdapter(Context context, int layoutId, List<NoticeListBean.NoticeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NoticeListBean.NoticeBean noticeBean, int position) {
           holder.setText(R.id.tv_day,noticeBean.day);
           holder.setText(R.id.tv_year,noticeBean.year);
           holder.setText(R.id.tv_title,noticeBean.title);
    }
}
