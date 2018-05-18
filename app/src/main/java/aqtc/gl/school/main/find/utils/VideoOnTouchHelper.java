package aqtc.gl.school.main.find.utils;

import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static aqtc.gl.school.main.common.FindCameraType.MODE_NONE;
import static aqtc.gl.school.main.common.FindCameraType.TYPE_IMG;
import static aqtc.gl.school.main.common.FindCameraType.TYPE_VIDEO;


/**
 * @author gl
 * @date 2018/5/18
 * @desc 发现拍照按钮点击处理,默认可录像可拍照，拍完照可以再拍，录完像不能再录
 */
public class VideoOnTouchHelper implements View.OnTouchListener {

    private int mode = MODE_NONE;

    private OnTouchCallback listener;

    private boolean isTaked = false;

    private long actionDownTime;

    private Timer timer = new Timer();

    public VideoOnTouchHelper(OnTouchCallback listener) {
        this.listener = listener;
    }

    public boolean isTakePhoto() {
        return mode == TYPE_IMG;
    }

    public boolean isRecord() {
        return mode == TYPE_VIDEO;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDownTime = event.getDownTime();
                //模式不确定才可以录像
                if (mode == MODE_NONE) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (mode != TYPE_VIDEO) {
                                mode = TYPE_VIDEO;
                                if (listener != null) {
                                    listener.startRecord();
                                }
                            }
                            timer.cancel();
                        }
                    }, 300);
                }
            case MotionEvent.ACTION_MOVE:
                long remainTime = event.getEventTime() - actionDownTime;
                if (remainTime <= 250) {
                    mode = TYPE_IMG;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (mode == TYPE_IMG && !isTaked) {
                    isTaked = true;
                    timer.cancel();
                    if (listener != null) {
                        listener.takePhoto();
                    }
                } else {
                    if (listener != null) {
                        listener.finishRecord();
                    }
                }
                break;

        }
        return true;
    }


    public static interface OnTouchCallback {
        public void takePhoto();

        public void startRecord();

        public void finishRecord();
    }
}
