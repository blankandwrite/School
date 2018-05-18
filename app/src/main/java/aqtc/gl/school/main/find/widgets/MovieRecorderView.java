package aqtc.gl.school.main.find.widgets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.library.log.LogX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aqtc.gl.school.R;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.find.utils.CameraUtils;
import aqtc.gl.school.main.find.utils.DeviceUtil;
import aqtc.gl.school.main.find.utils.SurfaceViewHelper;
import aqtc.gl.school.main.find.utils.VideoOnTouchHelper;
import aqtc.gl.school.utils.bitmap.BitmapUtil;


/**
 * @author gl
 * @date 2018/5/18
 * @desc 视频播放控件
 */
public class MovieRecorderView extends LinearLayout implements OnErrorListener {
    private static final String TAG = "MovieRecorderView";

    private Context context;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    private MediaRecorder mediaRecorder;
    private Camera camera;
    private Timer timer;// 计时器

    private int mWidth;// 视频录制分辨率宽度
    private int mHeight;// 视频录制分辨率高度
    private boolean isFirstOpen;// 是否一开始就打开摄像头
    private boolean isOpenCamera;
    private int recordMaxTime;// 最长拍摄时间
    private int timeCount;// 时间计数
    private File recordFile = null;// 视频文件
    private long createTime;
    private long sizePicture = 0;

    //图片数据
    private long picCreateTime;
    private File picFile = null;// 图片文件

    private SurfaceViewHelper helper;
    private GestureDetector mDetector;
    private OnTouchListener mOnSurfaveViewTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (camera == null || !isOpenCamera) {
                return false;
            }
            return mDetector.onTouchEvent(event);
        }

    };
    private VideoOnTouchHelper modeHelper;

    public MovieRecorderView(Context context) {
        this(context, null);
    }

    public MovieRecorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        helper = SurfaceViewHelper.newInstance(context);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MovieRecorderView, defStyle, 0);
        mWidth = a.getInteger(R.styleable.MovieRecorderView_record_width, 640);// 默认640
        mHeight = a
                .getInteger(R.styleable.MovieRecorderView_record_height, 360);// 默认360

        isFirstOpen = a.getBoolean(
                R.styleable.MovieRecorderView_is_open_camera, true);// 默认打开摄像头
        recordMaxTime = a.getInteger(
                R.styleable.MovieRecorderView_record_max_time, 10);// 默认最大拍摄时间为10s

        LayoutInflater.from(context)
                .inflate(R.layout.find_movie_recorder_view, this);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mDetector = new GestureDetector(context, new ZoomGestureListener());
        surfaceView.setOnTouchListener(mOnSurfaveViewTouchListener);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new CustomCallBack());
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        a.recycle();
    }

    public void changeCamera() {
        camera = helper.changePreview(camera, surfaceHolder);
        isOpenCamera = true;
        try {
            initCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isOpenCamera = false;
    }

    public void takePhoto(final OnTakePhotoFinishListener takePhotoFinishListener) {
        camera.takePicture(null, null, new TakePhotoCallBack(takePhotoFinishListener));
    }

    public void setModeHelper(VideoOnTouchHelper modeHelper) {
        this.modeHelper = modeHelper;
    }

    private class TakePhotoCallBack implements Camera.PictureCallback {
        private OnTakePhotoFinishListener takePhotoFinishListener;

        public TakePhotoCallBack(OnTakePhotoFinishListener takePhotoFinishListener) {
            this.takePhotoFinishListener = takePhotoFinishListener;
        }

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //save the picture to sdcard
            File pictureFile = createPhotoDir();
            if (pictureFile == null) {
                LogX.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }
            Bitmap bMapRotate = null;
            try {
                Bitmap origin = BitmapFactory.decodeByteArray(data, 0, data.length);
                Configuration config = getResources().getConfiguration();
                int rotate = 0;
                if (config.orientation == Configuration.ORIENTATION_PORTRAIT) { // 坚拍
                    System.out.print("#do rotate");
                    if (helper.cameraPosition == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        rotate = 90;
                    } else if (helper.cameraPosition == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        rotate = 270;
                    }
                }
                bMapRotate = BitmapUtil.rotateToDegrees(origin, rotate);
                boolean status = BitmapUtil.saveBitmap(bMapRotate, pictureFile);
                if (status && takePhotoFinishListener != null) {
                    takePhotoFinishListener.onTakePhoto(pictureFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SurfaceHolder回调
     */
    private class CustomCallBack implements Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!isFirstOpen)
                return;
            try {
                initCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (!isFirstOpen)
                return;
            freeCameraResource();
        }
    }

    /**
     * 初始化摄像头
     */
    public void initCamera() throws IOException {
        if (!isOpenCamera) {
            if (camera != null) {
                freeCameraResource();
            }
            try {
                if (checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK)) {
                    camera = helper.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                } else if (checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
                    camera = helper.open(Camera.CameraInfo.CAMERA_FACING_FRONT);// TODO
                }
            } catch (Exception e) {
                e.printStackTrace();
                freeCameraResource();
                ((Activity) context).finish();
            }
        }
        if (camera == null)
            return;
        setCameraParams();
        setPicParams();
        initSizePicture();
        setDisplayOrientation();
        camera.setPreviewDisplay(surfaceHolder);
        camera.startPreview();
        lockOrUnlock();
        isOpenCamera = true;
    }

    private void setDisplayOrientation() {
        int rotation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 270;
                break;
        }
        int result;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(0, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degree) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degree + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private void setPicParams() {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            // 设置照片的格式
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置JPG照片的质量
            parameters.setJpegQuality(100);
            parameters.setJpegThumbnailQuality(100);
            camera.setParameters(parameters);
        }
    }

    /**
     * 检查是否有摄像头
     *
     * @param facing 前置还是后置
     * @return
     */
    private boolean checkCameraFacing(int facing) {
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    private void initSizePicture() {
        if (camera != null) {
            Parameters params = camera.getParameters();
            List<Size> supportedPictureSizes = params
                    .getSupportedPictureSizes();
            for (Size size : supportedPictureSizes) {
                sizePicture = (size.height * size.width) > sizePicture ? size.height
                        * size.width
                        : sizePicture;
            }
        }
    }

    /**
     * 设置摄像头为竖屏
     */
    private void setCameraParams() {
        if (camera != null) {
            Parameters params = camera.getParameters();
            params.set("orientation", "portrait");
            params.setWhiteBalance(Parameters.WHITE_BALANCE_AUTO);
            setPreviewSize(params);
            if (params.getSupportedFocusModes().contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            camera.setParameters(params);
        }
    }

    /**
     * 根据手机支持的视频分辨率，设置预览尺寸
     *
     * @param params
     */
    private void setPreviewSize(Parameters params) {
        if (camera == null) {
            return;
        }
        // LogUtil.e(LOG_TAG, "BestSize: width:" + best.width + "...height:" +
        // best.height);
        // List<int[]> range = params.getSupportedPreviewFpsRange();
        // int[] fps = range.get(0);
        // LogUtil.e(LOG_TAG,"min="+fps[0]+",max="+fps[1]);
        // params.setPreviewFpsRange(3,7);
        Size best = null;
        Point size = new Point();
        size.x = surfaceView.getMeasuredWidth();
        size.y = surfaceView.getMeasuredHeight();
        best = getBestCameraRatio(params, size);// 1920 1080 1280 960
        params.setPreviewSize(best.width, best.height);// 预览比率
        Log.e(TAG, "#setPreviewSize BestSize: width:" + best.width
                + "...height:" + best.height);

        // TODO 大部分手机支持的预览尺寸和录制尺寸是一样的，也有特例，有些手机获取不到，那就把设置录制尺寸放到设置预览的方法里面
        if (params.getSupportedVideoSizes() == null
                || params.getSupportedVideoSizes().size() == 0) {
            mWidth = best.width;
            mHeight = best.height;
        } else {
            setVideoSize(params, size);
        }
        if (params.getSupportedPictureSizes() != null
                || params.getSupportedPictureSizes().size() > 0) {
            Size bestPicSize = CameraUtils.getBestSize(params.getSupportedPictureSizes(), size);
            LogX.d("#bestPicSize:" + bestPicSize.width + " " + bestPicSize.height);
            params.setPictureSize(bestPicSize.width, bestPicSize.height);//拍照保存比率
        }
    }


    private Size getBestCameraRatio(Parameters parameters,
                                    Point screenResolution) {
        Size best = null;
        List<Size> supportedPreviewSizes = parameters
                .getSupportedPreviewSizes();
        best = CameraUtils.getOptimalPreviewSize(supportedPreviewSizes, screenResolution);
        Log.e(TAG, "#getBestCameraResolution BestSize: width:" + best.width
                + "...height:" + best.height);
        return best;
    }

    /**
     * 根据手机支持的视频分辨率，设置录制尺寸
     *
     * @param params
     */
    private void setVideoSize(Parameters params, Point size) {
        if (camera == null) {
            return;
        }
        Size best = CameraUtils.getBestSize(params.getSupportedVideoSizes(), size);
        // 设置录制尺寸
        mWidth = best.width;
        mHeight = best.height;
    }

    /**
     * 释放摄像头资源
     */
    private void freeCameraResource() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.lock();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            camera = null;
        }
    }

    /**
     * 创建视频文件
     */
    private void createRecordDir() {
        File sampleDir = new File(Global.getVideoPath(getContext()).getDirPath());
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        try {
            // TODO 文件名用的时间戳，可根据需要自己设置，格式也可以选择3gp，在初始化设置里也需要修改
            createTime = System.currentTimeMillis();
            recordFile = new File(sampleDir, createTime + ".mp4");
            // recordFile = new File(sampleDir, System.currentTimeMillis() +
            // ".mp4");
            // File.createTempFile(AccountInfo.userId, ".mp4", sampleDir);
            // LogUtil.e(LOG_TAG, recordFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建图片文件
     */
    private File createPhotoDir() {
        File sampleDir = new File(Global.getPicPath(getContext()).getDirPath());
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        try {
            picCreateTime = System.currentTimeMillis();
            picFile = new File(sampleDir, picCreateTime + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
            picFile = null;
        }
        return picFile;
    }

    /**
     * 录制视频初始化
     */
    private void initRecord() throws Exception {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();
        if (camera != null)
            mediaRecorder.setCamera(camera);
        lockOrUnlock();
        mediaRecorder.setOnErrorListener(this);
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setVideoSource(VideoSource.CAMERA);// 视频源
        mediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
        mediaRecorder.setOutputFormat(OutputFormat.MPEG_4);// TODO 视频输出格式
        // 也可设为3gp等其他格式
        mediaRecorder.setAudioEncoder(AudioEncoder.AAC);// 音频格式,注意ios必须要用AAC格式否则录制后到ios那边无法播放
        mediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率
        // mediaRecorder.setVideoFrameRate(25);//TODO 设置每秒帧数
        // 这个设置有可能会出问题，有的手机不支持这种帧率就会录制失败，这里使用默认的帧率，当然视频的大小肯定会受影响
        LogX.e(TAG, "手机支持的最大像素supportedPictureSizes====" + sizePicture);
        if (sizePicture < 3000000) {// 这里设置可以调整清晰度
            mediaRecorder.setVideoEncodingBitRate(4 * 1024 * 1024);
        } else if (sizePicture <= 5000000) {
            mediaRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
        } else {
            mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        }
        if (helper.cameraPosition == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
        } else if (helper.cameraPosition == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mediaRecorder.setOrientationHint(270);// 输出旋转90度，保持竖屏录制
        }

        mediaRecorder.setVideoEncoder(VideoEncoder.H264);// 视频录制格式
        // mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
        mediaRecorder.prepare();
        mediaRecorder.start();
    }

    /**
     * 开始录制视频
     *
     * @param onRecordFinishListener 达到指定时间之后回调接口
     */
    public void record(final OnRecordFinishListener onRecordFinishListener) {
        this.onRecordFinishListener = onRecordFinishListener;
        createRecordDir();
        try {
            if (!isFirstOpen)
                initCamera();
            initRecord();
            timeCount = 0;// 时间计数器重新赋值
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeCount++;
                    if (onRecordProgressListener != null) {
                        onRecordProgressListener.onProgressChanged(
                                recordMaxTime, timeCount);
                    }

                    // 达到指定时间，停止拍摄
                    if (timeCount == recordMaxTime) {
                        stop();
                        if (MovieRecorderView.this.onRecordFinishListener != null)
                            MovieRecorderView.this.onRecordFinishListener
                                    .onRecordFinish(getRecordFile());
                    }
                }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
            if (mediaRecorder != null) {
                mediaRecorder.release();
            }
            freeCameraResource();
        }
    }

    /**
     * 停止拍摄
     */
    public void stop() {
        stopRecord();
        releaseRecord();
        freeCameraResource();
    }

    /**
     * 停止录制
     */
    public void stopRecord() {
        if (onRecordProgressListener != null) {
            onRecordProgressListener.onProgressChanged(
                    recordMaxTime, 0);
        }
        if (timer != null)
            timer.cancel();
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);// 设置后防止崩溃
            mediaRecorder.setPreviewDisplay(null);
            try {
                mediaRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     */
    private void releaseRecord() {
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);
            try {
                mediaRecorder.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mediaRecorder = null;
    }

    /**
     * 获取当前录像时间
     *
     * @return timeCount
     */
    public int getTimeCount() {
        return timeCount;
    }

    /**
     * 设置最大录像时间
     *
     * @param recordMaxTime
     */
    public void setRecordMaxTime(int recordMaxTime) {
        this.recordMaxTime = recordMaxTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    /**
     * 返回录像文件
     *
     * @return recordFile
     */
    public File getRecordFile() {
        return recordFile;
    }

    /**
     * 录制完成监听
     */
    private OnRecordFinishListener onRecordFinishListener;

    /**
     * 录制完成接口
     */
    public interface OnRecordFinishListener {
        void onRecordFinish(File file);
    }

    /**
     * 拍照完成接口
     */
    public interface OnTakePhotoFinishListener {
        void onTakePhoto(File filePath);
    }

    /**
     * 录制进度监听
     */
    private OnRecordProgressListener onRecordProgressListener;

    /**
     * 设置录制进度监听
     *
     * @param onRecordProgressListener
     */
    public void setOnRecordProgressListener(
            OnRecordProgressListener onRecordProgressListener) {
        this.onRecordProgressListener = onRecordProgressListener;
    }

    /**
     * 录制进度接口
     */
    public interface OnRecordProgressListener {
        /**
         * 进度变化
         *
         * @param maxTime     最大时间，单位秒
         * @param currentTime 当前进度
         */
        void onProgressChanged(int maxTime, int currentTime);
    }

    public void setZoom(int zoomValue) {
        try {
            camera.reconnect();
            Parameters parameters = camera.getParameters();
            if (parameters.isZoomSupported()) {
                final int MAX = parameters.getMaxZoom();
                if (MAX == 0)
                    return;
                if (zoomValue > MAX)
                    zoomValue = MAX;
                parameters.setZoom(zoomValue); // value zoom value. The valid
                // range
                camera.setParameters(parameters);
                lockOrUnlock();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void lockOrUnlock() {
        if (modeHelper.isRecord()) {
            camera.unlock();
        } else {
            camera.lock();
        }
    }

    class ZoomGestureListener extends SimpleOnGestureListener {
        private boolean mZoomIn = true;

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (camera == null || !isOpenCamera) {
                return false;
            }

            if (!mZoomIn) {
                setZoom(10); // zoom in..
                mZoomIn = true;
            } else {
                setZoom(0); // zoom out..
                mZoomIn = false;
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            checkCameraFocus(e);
            return true;
        }
    }

    private void checkCameraFocus(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float touchMajor = event.getTouchMajor();
        float touchMinor = event.getTouchMinor();
        Rect touchRect = new Rect();
        // 触摸范围
        if (touchMajor == 0 && touchMinor == 0) {
            touchMajor = 100;
            touchMinor = 100;
        }
        touchRect.set((int) (x - touchMajor / 2), (int) (y - touchMinor / 2),
                (int) (x + touchMajor / 2), (int) (y + touchMinor / 2));

        LogX.d("#touchMajor:" + touchMajor + " touchMinor:"
                + touchMinor + " x:" + x + " y:" + y);

        // 坐标转换为focusArea范围
        Rect focusRect = new Rect();
        focusRect.set(touchRect.left * 2000 / surfaceView.getWidth() - 1000,
                touchRect.top * 2000 / surfaceView.getHeight() - 1000,
                touchRect.right * 2000 / surfaceView.getWidth() - 1000,
                touchRect.bottom * 2000 / surfaceView.getHeight() - 1000);
        LogX.d("#focusRect:" + focusRect + " touchRect:"
                + touchRect);
        LogX.d("#width:" + surfaceView.getWidth() + " height:"
                + surfaceView.getHeight());
        if (focusRect.left >= focusRect.right
                || focusRect.top >= focusRect.bottom)
            return;

        ArrayList<Area> focusAreas = new ArrayList<Area>();
        focusAreas.add(new Area(focusRect, 1000));
        if (!manualFocus(new AutoFocusCallback() {

            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                // if (success) {
                LogX.d("#onAutoFocus previewsize..width = "
                        + camera.getParameters().getPreviewSize().width
                        + "\nheight = "
                        + camera.getParameters().getPreviewSize().height);
                // }
            }
        }, focusAreas)) {
        }

    }

    public boolean manualFocus(AutoFocusCallback cb, List<Area> focusAreas) {
        // 判断系统是否是4.0以上的版本
        if (camera != null && DeviceUtil.isICS()) {
            try {
                camera.reconnect();
                camera.cancelAutoFocus();
                Parameters parameters = camera.getParameters();
                if (parameters != null) {
                    if (focusAreas != null) {
                        // getMaxNumFocusAreas检测设备是否支持
                        if (parameters.getMaxNumFocusAreas() > 0) {
                            parameters.setFocusAreas(focusAreas);
                        }
                        // getMaxNumMeteringAreas检测设备是否支持
                        if (parameters.getMaxNumMeteringAreas() > 0) {
                            parameters.setMeteringAreas(focusAreas);
                        }
                    }
                    parameters.setFocusMode(Parameters.FOCUS_MODE_MACRO);
                    camera.setParameters(parameters);
                    camera.autoFocus(cb);
                    LogX.d("#manualFocus");
                    lockOrUnlock();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (e != null)
                    Log.e(" ", "autoFocus", e);
            }
        }
        return false;
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        try {
            if (mr != null)
                mr.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}