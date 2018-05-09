package aqtc.gl.school.fragment.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import aqtc.gl.school.R;

/**
 * @author gl
 * @date 2018/5/9
 * @desc
 */
public class AcademyAdpter extends CommonRecyclerRecyclerAdapter<String> {

    public AcademyAdpter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
          holder.setText(R.id.tv_name,s);
    }
}
