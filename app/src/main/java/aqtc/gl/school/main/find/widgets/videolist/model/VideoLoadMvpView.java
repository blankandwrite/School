package aqtc.gl.school.main.find.widgets.videolist.model;

import android.media.MediaPlayer;



import aqtc.gl.school.main.find.widgets.videolist.widget.TextureVideoView;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public interface VideoLoadMvpView {

    TextureVideoView getVideoView();

    void videoBeginning();

    void videoStopped();

    void videoPrepared(MediaPlayer player);

    void videoResourceReady(String videoPath);
}
