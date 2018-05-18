package aqtc.gl.school.main.find.utils;

import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 相机工具类
 */
public class CameraUtils {
    private static final String TAG = "CameraUtils";

    public static Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, Point targetSize) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) targetSize.x / targetSize.y;
        if (sizes == null)
            return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = targetSize.y;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            // 此处注意:camera的宽为屏幕竖屏的高,camera的高为屏幕竖屏的宽
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public static Camera.Size getBestSize(List<Camera.Size> sizes, Point targetSize) {
        Collections.sort(sizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                if (lhs.width > rhs.width) {
                    return -1;
                } else if (lhs.width == rhs.width) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        float tmp = 0f;
        float minDiff = 100f;
        float ratio = (float) targetSize.x / targetSize.y;// 高宽比率3:4，且最接近屏幕宽度的分辨率
        Camera.Size best = null;
        float exceptBig = 0;
        for (Camera.Size s : sizes) {
            tmp = Math.abs(((float) s.height / (float) s.width) - ratio);
            exceptBig = (float) s.height / (float) targetSize.x;
            if (exceptBig > 1.5 || exceptBig < 0.5) {//首先排除比我们屏幕大太多的,小太多的
                continue;
            }

            if (tmp < minDiff) {
                minDiff = tmp;
                best = s;
            }
            if (exceptBig >= 1.0 && exceptBig <= 1.3 && best != null) {
                break;
            }
        }

        if (best == null) {
            tmp = 0f;
            minDiff = 100f;
            for (Camera.Size s : sizes) {
                tmp = Math.abs(((float) s.height / (float) s.width) - ratio);
                if (tmp < minDiff) {
                    minDiff = tmp;
                    best = s;
                }
            }
        }
        Log.e(TAG, "get BestSize: width:" + best.width
                + "...height:" + best.height);
        return best;
    }
}