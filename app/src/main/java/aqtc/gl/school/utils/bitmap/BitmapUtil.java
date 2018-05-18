package aqtc.gl.school.utils.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.library.log.LogX;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 图片工具类
 */
public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();

    /**
     * 尝试进行压缩
     *
     * @param bitmap 图片对象
     * @param expectSize 期望的文件大小以下
     * @return 压缩后的数据
     */
    public static byte[] compressBitmap(Bitmap bitmap, int expectSize) {
        boolean isCompress = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        byte[] datas = baos.toByteArray();
        Log.e(TAG, "#compress start size:" + (datas.length / 1024));
        // 循环判断如果压缩后图片是否大于size KB
        while (datas.length / 1024 > expectSize) {
            if ((options -= 5) < 50)// 每次都减少5
            {
                break;
            }
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            datas = baos.toByteArray();
            Log.e(TAG, "#compress doing:" + datas.length / 1024);
            isCompress = true;
        }
        Log.e(TAG, "#compress end options:" + options);
        if (isCompress) {
            Log.e(TAG, "#compress finish size:" + (datas.length / 1024));
            // 把数据转成图片后再保存会导致大小再次变大，估计与Options Config有关
            /*
             * Options opts = new Options(); ByteArrayInputStream isBm = new
			 * ByteArrayInputStream(datas);//
			 * 把压缩后的数据baos存放到ByteArrayInputStream中 opts.inPreferredConfig =
			 * Config.RGB_565; opts.inPurgeable = true; Bitmap bm =
			 * BitmapFactory.decodeStream(isBm, null, opts);//
			 * 把ByteArrayInputStream数据生成图片
			 */
            return datas;
        } else {
            return null;
        }

    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param expectWidth 期望宽度
     * @param expectHeight 期望高度
     * @return 返回采样率
     */
    public static int caculateInSampleSize(Options options, int expectWidth,
                                           int expectHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width * height == expectWidth * expectHeight) {
            return inSampleSize;
        }
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        // if (width > height && width > expectWidth) {// 如果宽度大的话根据宽度固定大小缩放
        // inSampleSize = (int) (width / expectWidth);
        // } else if (width < height && height > expectHeight) {//
        // 如果高度高的话根据宽度固定大小缩放
        // inSampleSize = (int) (height / expectHeight);
        // }
        // inSampleSize = (int) ((width / expectWidth + height / expectHeight)
        // / 2);
        if (width > expectWidth || height > expectHeight) {
            int widthRadio = (int) Math.round(width * 1.0f / expectWidth);
            int heightRadio = (int) Math.round(height * 1.0f / expectHeight);
            LogX.d("#widthRadio:" + widthRadio + " heightRadio:"
                    + heightRadio);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        if (inSampleSize <= 0)
            inSampleSize = 1;

        return inSampleSize;
    }

    /**
     * 图片旋转
     *
     * @param originBitmap 原图片
     * @param degrees 旋转角度
     * @return 旋转后图片
     */
    public static Bitmap rotateToDegrees(Bitmap originBitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degrees);
        Bitmap tmp =
                Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix,
                        true);
        if (tmp != originBitmap) {
            return tmp;
        }
        return originBitmap;
    }

    /**
     * 图片保存
     *
     * @param bitmap 图片对象
     * @param file 准备保存的文件路径
     * @return 是否保存成功
     */
    public static boolean saveBitmap(Bitmap bitmap, File file) {
        boolean status = false;
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            //必须为PNG否则会有黑色背景
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            status = true;
            LogX.d("#saveBitmap:" + status);
        } catch (Exception e) {
            status = false;
            Log.d(TAG, "#file save error: " + e.getMessage());
        }
        return status;
    }

    /**
     * view转成图片
     *
     * @param view 被转成图片的view
     * @return 生成的图片对象
     */
    public static Bitmap loadBitmapFromView(View view) {
        int w = view.getWidth();
        int h = view.getHeight();
        w = w <= 0 ? view.getMeasuredWidth() : w;
        h = h <= 0 ? view.getMeasuredHeight() : h;
        if (w <= 0 || h <= 0)
            return null;
        view.destroyDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.draw(new Canvas(bitmap));
        Bitmap tmp = rotateToDegrees(bitmap, view.getRotation());
        System.out.println("#bitmap:" + tmp.getWidth() + "->" + tmp.getHeight());
        return tmp;
    }

    //TODO 待测试
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap getBitmap(View view) {
        Bitmap bitmap = null;
        int width = view.getRight() - view.getLeft();
        int height = view.getBottom() - view.getTop();
        final boolean opaque = view.getDrawingCacheBackgroundColor() != 0 || view.isOpaque();
        Bitmap.Config quality;
        if (!opaque) {
            switch (view.getDrawingCacheQuality()) {
                case View.DRAWING_CACHE_QUALITY_AUTO:
                case View.DRAWING_CACHE_QUALITY_LOW:
                case View.DRAWING_CACHE_QUALITY_HIGH:
                default:
                    quality = Bitmap.Config.ARGB_8888;
                    break;
            }
        } else {
            quality = Bitmap.Config.RGB_565;
        }
        bitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(),
                width, height, quality);
        bitmap.setDensity(view.getResources().getDisplayMetrics().densityDpi);
        if (opaque) bitmap.setHasAlpha(false);
        boolean clear = view.getDrawingCacheBackgroundColor() != 0;
        Canvas canvas = new Canvas(bitmap);
        if (clear) {
            bitmap.eraseColor(view.getDrawingCacheBackgroundColor());
        }
        view.computeScroll();
        final int restoreCount = canvas.save();
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);
        canvas.restoreToCount(restoreCount);
        canvas.setBitmap(null);
        return bitmap;
    }
}
