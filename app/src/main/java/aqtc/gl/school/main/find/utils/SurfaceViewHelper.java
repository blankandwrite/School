package aqtc.gl.school.main.find.utils;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.view.SurfaceHolder;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 摄像头操作，前后切换等
 */
public class SurfaceViewHelper {
    public int cameraPosition = CameraInfo.CAMERA_FACING_BACK;
    private Context context;

    private SurfaceViewHelper() {

    }

    public static SurfaceViewHelper newInstance(Context context) {
        SurfaceViewHelper instance = new SurfaceViewHelper();
        instance.context = context;
        return instance;
    }

    public Camera open(int position) {
        cameraPosition = position;
        return Camera.open(position);
    }

    public int getCameraCount() {
        return Camera.getNumberOfCameras();
    }

    public Camera changePreview(Camera camera, SurfaceHolder holder) { // 切换前后摄像头
        // 切换前后摄像头
        int cameraCount = 0;
        CameraInfo cameraInfo = new CameraInfo();
        cameraCount = getCameraCount();// 得到摄像头的个数

        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);// 得到每一个摄像头的信息
            if (cameraPosition == 1) {
                // 现在是后置，变更为前置
                if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {// 代表摄像头的方位，CAMERA_FACING_FRONT前置
                    camera = doChangePreview(cameraInfo, camera, holder,
                            CameraInfo.CAMERA_FACING_BACK);
                    break;
                }
            } else {
                // 现在是前置， 变更为后置
                if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {// 代表摄像头的方位，CAMERA_FACING_FRONT前置
                    camera = doChangePreview(cameraInfo, camera, holder,
                            CameraInfo.CAMERA_FACING_FRONT);
                    break;
                }
            }

        }
        return camera;
    }

    private int calOrientation(CameraInfo info, int orientation) {
        orientation = (orientation + 45) / 90 * 90;
        int rotation = 0;
        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {
            rotation = (info.orientation + orientation) % 360;
        }
        return rotation;
    }

    private void freeCamera(Camera camera) {
        try {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.lock();
            camera.release();
            camera = null;// 取消原来摄像头
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Camera doChangePreview(CameraInfo info, Camera camera,
                                   SurfaceHolder holder, int position) {
        freeCamera(camera);
        try {
            camera = Camera.open(position);// 打开当前选中的摄像头
            camera.setPreviewDisplay(holder);// 通过surfaceview显示取景画面
            // int orientation =
            // context.getResources().getConfiguration().orientation;
            camera.setDisplayOrientation(90);
            camera.startPreview();// 开始预览
            cameraPosition = position;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return camera;
    }
}