package aqtc.gl.school.main.find.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import aqtc.gl.school.R;

import static aqtc.gl.school.main.common.FindCameraType.TYPE_URL;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public class URLViewHolder extends CircleViewHolder{
    public LinearLayout urlBody;
    /** 链接的图片 */
    public ImageView urlImageIv;
    /** 链接的标题 */
    public TextView urlContentTv;

    public URLViewHolder(View itemView){
        super(itemView, TYPE_URL);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }

        viewStub.setLayoutResource(R.layout.viewstub_urlbody);
        View subViw  = viewStub.inflate();
        LinearLayout urlBodyView = (LinearLayout) subViw.findViewById(R.id.urlBody);
        if(urlBodyView != null){
            urlBody = urlBodyView;
            urlImageIv = (ImageView) subViw.findViewById(R.id.urlImageIv);
            urlContentTv = (TextView) subViw.findViewById(R.id.urlContentTv);
        }
    }
}
