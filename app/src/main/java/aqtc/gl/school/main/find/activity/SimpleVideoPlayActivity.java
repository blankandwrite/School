package aqtc.gl.school.main.find.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.widgets.videolist.widget.TextureVideoView;
import aqtc.gl.school.utils.file.MimeTypeMap;
import aqtc.gl.school.utils.image.ImageLoad;


/**
 * @author gl
 * @date 2018/5/18
 * @desc 简易视频播放
 */
public class SimpleVideoPlayActivity extends AppCompatActivity implements TextureVideoView.MediaPlayerCallback {
    public static final String THUMBNAIL_URL = "thumbnail_url";
    public static final String URL = "url";
    private String url;
    private String thumbnailUrl;

    private ProgressBar videoProgress;
    private ImageView videoFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_layout_simple_video);
        resolveIntent();
        final TextureVideoView videoView = (TextureVideoView) findViewById(R.id.textureVideoView);
        videoProgress = (ProgressBar) findViewById(R.id.video_progress);
        videoFrame = (ImageView) findViewById(R.id.videoFrame);
        if (!TextUtils.isEmpty(url)) {
            if (MimeTypeMap.isVideo(url)) {
                videoView.setVideoPath(url);
                videoView.start();
            }
        }
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            ImageLoad.getImageGlide(SimpleVideoPlayActivity.this, thumbnailUrl, R.drawable.img_default_black).centerCrop().into(videoFrame);
        }
        videoProgress.setVisibility(View.VISIBLE);
        videoView.setMediaPlayerCallback(this);
    }


    public static void openVideo(Context context, String thumbnailUrl, String url) {
        Intent intent = new Intent(context, SimpleVideoPlayActivity.class);
        intent.putExtra(THUMBNAIL_URL, thumbnailUrl);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    private void resolveIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra(URL);
        thumbnailUrl = intent.getStringExtra(THUMBNAIL_URL);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoFrame.setVisibility(View.GONE);
        videoProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStoped(MediaPlayer mp) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}