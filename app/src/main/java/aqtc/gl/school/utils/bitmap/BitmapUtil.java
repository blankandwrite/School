package aqtc.gl.school.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import aqtc.gl.school.utils.LogX;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 图片工具类
 */
public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();

    public static byte[] compressBitmap(Bitmap bitmap, int expectSize) {
        boolean isCompress = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        byte[] datas = baos.toByteArray();
        LogX.e(TAG, "#compress start size:" + (datas.length / 1024));
        // 循环判断如果压缩后图片是否大于size KB
        while (datas.length / 1024 > expectSize) {
            if ((options -= 10) < 40)// 每次都减少10
            {
                break;
            }
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            datas = baos.toByteArray();
            LogX.e(TAG, "#compress doing:" + datas.length / 1024);
            isCompress = true;
        }
        LogX.e(TAG, "#compress end options:" + options);
        if (isCompress) {
            LogX.e(TAG, "#compress finish size:" + (datas.length / 1024));
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
     * @param expectWidth
     * @param expectHeight
     * @return
     */
    public static int caculateInSampleSize(Options options, int expectWidth,
                                           int expectHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width * height == expectWidth * expectHeight) {
            return inSampleSize;
        }
        LogX.e(TAG, "#caculateInSampleSize origin width:" + width + " height:"
                + height);
        LogX.e(TAG, "#caculateInSampleSize expect width:" + expectWidth
                + " height:" + expectHeight);
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
            Log.d(TAG, "#widthRadio:" + widthRadio + " heightRadio:"
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
     * @param originBitmap
     * @param degrees
     * @return
     */
    public static Bitmap rotateToDegrees(Bitmap originBitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degrees);
        Bitmap tmp =
                Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix,
                        true);
        if (tmp != originBitmap) {
            originBitmap.recycle();
        }
        return tmp;
    }

    public static boolean saveBitmap(Bitmap bitmap, File file) {
        boolean status = false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            status = true;
        } catch (Exception e) {
            status = false;
            Log.d(TAG, "save file error: " + e.getMessage());
        }
        return status;
    }
}
