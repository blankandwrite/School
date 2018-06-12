package aqtc.gl.school.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.bean.CircleItemServer;
import aqtc.gl.school.utils.Utils;
import aqtc.gl.school.utils.image.ImageLoad;
import aqtc.gl.school.widget.flowlayout.FlowLayout;
import aqtc.gl.school.widget.flowlayout.wrapper.TagAdapter;
import aqtc.gl.school.widget.flowlayout.wrapper.TagFlowLayout;

/**
 * @author gl
 * @date 2018/6/12
 * @desc 点赞控件
 */
public class PraiseLayout extends LinearLayout {
    private TagFlowLayout praiseFlowLayout;
    private Context context;

    public PraiseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        praiseFlowLayout = (TagFlowLayout) findViewById(R.id.praiseFlowLayout);
    }

    public void setDatas(final List<CircleItemServer.ListBean.LikeListBean> datas) {
        praiseFlowLayout.setAdapter(new TagAdapter<CircleItemServer.ListBean.LikeListBean>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, CircleItemServer.ListBean.LikeListBean o) {
                ImageView img = new ImageView(context);
                ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(Utils.getPxByDp(30, context), Utils.getPxByDp(30, context));
                layoutParams.leftMargin = Utils.getPxByDp(5, context);
                img.setLayoutParams(layoutParams);
            //    ImageLoad.getHeadGlide(context, datas.get(position).getUser().getHeadImage(), 0, R.color.translucent_gray7).into(img);
                ImageLoad.loadRoundImage(context,datas.get(position).getUser().getHeadUrl(),R.mipmap.ic_user,img);
                return img;
            }
        });
    }

    public void setOnTagClickListener(TagFlowLayout.OnTagClickListener onTagClickListener) {
        praiseFlowLayout.setOnTagClickListener(onTagClickListener);
    }
}
