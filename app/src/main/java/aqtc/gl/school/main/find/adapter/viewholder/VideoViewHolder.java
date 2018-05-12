package aqtc.gl.school.main.find.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.widgets.CircleVideoView;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public class VideoViewHolder extends CircleViewHolder {

    public CircleVideoView videoView;

    public VideoViewHolder(View itemView){
        super(itemView, TYPE_VIDEO);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        
        viewStub.setLayoutResource(R.layout.viewstub_videobody);
        View subView = viewStub.inflate();

        CircleVideoView videoBody = (CircleVideoView) subView.findViewById(R.id.videoView);
        if(videoBody!=null){
            this.videoView = videoBody;
        }
    }
}
