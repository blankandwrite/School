package aqtc.gl.school.main.find.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.utils.SdcardFileSaveDbHelper;
import aqtc.gl.school.main.find.utils.SurfaceViewHelper;
import aqtc.gl.school.main.find.utils.VideoOnTouchHelper;
import aqtc.gl.school.main.find.widgets.FindTakePhotoButton;
import aqtc.gl.school.main.find.widgets.MovieRecorderView;
import aqtc.gl.school.utils.ToastUtils;

import static aqtc.gl.school.main.common.FindCameraType.CAMERA_MODE;
import static aqtc.gl.school.main.common.FindCameraType.MODE_NONE;
import static aqtc.gl.school.main.find.adapter.viewholder.CircleViewHolder.TYPE_VIDEO;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 拍照录像二合一
 */
public class VideoRecorderActivity extends AppCompatActivity implements VideoOnTouchHelper.OnTouchCallback, MovieRecorderView.OnTakePhotoFinishListener {
    private MovieRecorderView mRecorderView;
    private TextView modeToast;
    private FindTakePhotoButton mShootBtn;
    private View changeCamera;

    private boolean isFinish = true;
    private VideoOnTouchHelper helper;
    private int mode;
    private ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_video_recorder);
        resolveIntent();

        mRecorderView = (MovieRecorderView) findViewById(R.id.movieRecorderView);
        modeToast = (TextView) findViewById(R.id.modeToast);
        mShootBtn = (FindTakePhotoButton) findViewById(R.id.shoot_button);
        changeCamera = findViewById(R.id.change);

        if (mode == MODE_NONE) {
            modeToast.setText("轻触拍照，长按摄像");
        } else if (mode == TYPE_VIDEO) {
            modeToast.setText("长按摄像");
        } else {
            modeToast.setText("轻触拍照");
        }
        helper = new VideoOnTouchHelper(this);
        helper.setMode(mode);
        mShootBtn.setOnTouchListener(helper);
        mRecorderView.setModeHelper(helper);
        //切换前后摄像头
        int count = SurfaceViewHelper.newInstance(this).getCameraCount();
        if (count <= 1) {
            changeCamera.setVisibility(View.GONE);
        } else {
            changeCamera.setVisibility(View.VISIBLE);
            changeCamera.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mRecorderView.changeCamera();
                }
            });
        }
    }


    private void resolveIntent() {
        Intent intent = getIntent();
        if (intent == null) {
            intent = new Intent();
        }
        mode = intent.getIntExtra(CAMERA_MODE, MODE_NONE);
    }

    private void goBack() {
        if (isFinish) {
            mRecorderView.stop();
        }
        // 返回到播放页面
        Intent intent = getIntent();
        if (intent == null) {
            intent = new Intent();
        }
        if (datas != null && datas.size() > 0) {
            intent.putExtra(PhotoSelectActivity.SELECT_DATA, datas);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public void takePhoto() {
        mRecorderView.takePhoto(this);
    }

    public void startRecord() {
        mShootBtn.setMaxSecond(10);
        mShootBtn.autoUpdate();
        mRecorderView
                .record(new MovieRecorderView.OnRecordFinishListener() {
                    @Override
                    public void onRecordFinish(File file) {
                        finishRecord();
                    }
                });
    }

    @Override
    public void finishRecord() {
        mShootBtn.stopUpdate();
        if (mRecorderView.getTimeCount() > 1) {
            datas.add(mRecorderView.getRecordFile().getAbsolutePath());
            // 保存到系统库中
            SdcardFileSaveDbHelper.insertVideoToMediaStore(
                    VideoRecorderActivity.this, mRecorderView
                            .getRecordFile().getAbsolutePath(),
                    mRecorderView.getCreateTime(), mRecorderView
                            .getTimeCount() * 1000);
        } else {
            if (mRecorderView.getRecordFile() != null)
                mRecorderView.getRecordFile().delete();
            mRecorderView.stop();
            ToastUtils.showMsg(VideoRecorderActivity.this, "视频录制时间太短");
        }
        goBack();
    }

    @Override
    public void onTakePhoto(File filePath) {
        datas.add(filePath.getAbsolutePath());
        goBack();
    }

    @Override
    public void onResume() {
        super.onResume();
        isFinish = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFinish = false;
        mRecorderView.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
