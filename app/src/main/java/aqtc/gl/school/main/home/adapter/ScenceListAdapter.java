package aqtc.gl.school.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.utils.Utils;
import aqtc.gl.school.utils.image.ImageLoad;

/**
 * @author gl
 * @date 2018/5/9
 * @desc
 */
public class ScenceListAdapter extends CommonRecyclerRecyclerAdapter<String> {


    private final ArrayList<Integer> mHightList;
    private int hight;

    public ScenceListAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        hight= Utils.getScreenWidth(mContext)/3-20;
        mHightList = new ArrayList<>();
        for (String data : datas) {
            mHightList.add((int) (hight + Math.random() * 200));
        }
    }

    @Override
    protected void convert(ViewHolder holder, String item, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        CardView.LayoutParams params = (CardView.LayoutParams) imageView.getLayoutParams();
        params.height = mHightList.get(position);
        imageView.setLayoutParams(params);
        ImageLoad.loadImageFit(mContext,item,imageView,R.mipmap.image_weibo_home);



    }
}
