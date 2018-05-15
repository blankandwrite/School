package aqtc.gl.school.main.home.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.NewsListBean;

/**
 * @author gl
 * @date 2018/5/15
 * @desc
 */
public class TextItemDelagate implements ItemViewDelegate<NewsListBean.NewsBean> {
    private Context mContext;

    public TextItemDelagate(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_science_list;
    }

    @Override
    public boolean isForViewType(NewsListBean.NewsBean item, int position) {
        if (item.type==0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public void convert(ViewHolder holder, NewsListBean.NewsBean newsBean, int position) {
        holder.setText(R.id.tv_title, newsBean.title);
        holder.setText(R.id.tv_time, newsBean.time);
    }
}
