package aqtc.gl.school.main.find.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import aqtc.gl.school.R;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.find.bean.CircleItemServer;
import aqtc.gl.school.main.find.widgets.videolist.widget.TextureVideoView;
import aqtc.gl.school.utils.Utils;


/**
 * Created by yiwei on 16/5/23.
 */
public class CircleVideoView extends RelativeLayout {
    public int suggestWidth = 0;

    public TextureVideoView videoPlayer;
    public ImageView videoFrame;
    public ImageView videoButton;

    private String videoUrl;
    private Context context;

    public CircleVideoView(Context context) {
        this(context, null);
    }

    public CircleVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void start() {
        videoPlayer.start();
    }


    public void setVideoUrl(String url) {
        videoUrl = url;
    }

    public void setVideoImgUrl(final CircleItemServer.ListBean.PathListBean videoBean) {
        //ImageLoad.getImageGlide(getContext(), imgUrl,R.drawable.img_default).centerCrop().into(videoFrame);
        Glide.with(context).load(videoBean.getThumbnail()).asBitmap().placeholder(Global.IMAGE_DEFAULT).error(Global.IMAGE_DEFAULT).fallback(Global.IMAGE_DEFAULT).into(new BitmapImageViewTarget(videoFrame) {
            @Override
            protected void setResource(Bitmap resource) {
                if (!videoBean.isHasSize()) {
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    if (layoutParams == null) {
                        layoutParams = new ViewGroup.LayoutParams(suggestWidth, Utils.getPxByDp(200, context));
                    }
                    /*layoutParams.width = resource.getWidth();
                    layoutParams.height = resource.getHeight();
                    videoBean.w = resource.getWidth();
                    videoBean.h = resource.getHeight()
                    setLayoutParams(layoutParams);*/
                }
                videoFrame.setScaleType(ImageView.ScaleType.CENTER_CROP);
                videoFrame.setImageBitmap(resource);
            }
        });
    }

    private void init() {
        inflate(getContext(), R.layout.layout_video, this);
        videoPlayer = (TextureVideoView) findViewById(R.id.video_player);
        videoFrame = (ImageView) findViewById(R.id.iv_video_frame);
        videoButton = (ImageView) findViewById(R.id.iv_video_play);
        post(new Runnable() {
            @Override
            public void run() {
                suggestWidth = (int) (getWidth() / 1.5);
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(suggestWidth, Utils.getPxByDp(200, context));
                }
                layoutParams.width = suggestWidth;
                layoutParams.height = Utils.getPxByDp(200, context);
                setLayoutParams(layoutParams);
            }
        });
    }
}
