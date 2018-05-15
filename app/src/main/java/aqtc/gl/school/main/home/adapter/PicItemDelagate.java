package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import aqtc.gl.school.R;
import aqtc.gl.school.main.home.bean.NewsListBean;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/15
 * @desc
 */
public class PicItemDelagate implements ItemViewDelegate<NewsListBean.NewsBean> {
    private Context mContext;

    public PicItemDelagate(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_common_list;
    }

    @Override
    public boolean isForViewType(NewsListBean.NewsBean item, int position) {
        if (item.type==0){
            return false;
        }else {
            return true;
        }

    }

    @Override
    public void convert(ViewHolder holder, NewsListBean.NewsBean newsBean, int position) {
        holder.setText(R.id.tv_title, newsBean.title);
        holder.setText(R.id.tv_time, newsBean.time);
        ImageView imageView = holder.getView(R.id.iv_img);
        ImageLoad.loadImage(mContext ,newsBean.imageUrl,imageView,R.mipmap.no_image);
    }
}
