package aqtc.gl.school.main.find.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;

import aqtc.gl.school.R;
import aqtc.gl.school.main.common.FindCameraType;
import aqtc.gl.school.main.find.widgets.MultiImageView;


/**
 * @author gl
 * @date 2018/5/12
 * @desc 图片处理
 */
public class ImageViewHolder extends CircleViewHolder {
    /** 图片*/
    public MultiImageView multiImageView;

    public ImageViewHolder(View itemView){
        super(itemView, FindCameraType.TYPE_IMG);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        viewStub.setLayoutResource(R.layout.viewstub_imgbody);
        View subView = viewStub.inflate();
        MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
        if(multiImageView != null){
            this.multiImageView = multiImageView;
        }
    }
}
