package aqtc.gl.school.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.DisplayMetrics;
import android.util.Log;

import com.library.log.LogX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import aqtc.gl.school.utils.file.PathHelper;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 图片压缩
 */
public class BitmapCompresser {

    private static final String TAG = BitmapCompresser.class.getSimpleName();

    // 800kb,限制上传文件大小
    private static final int EXPECT_FILE_SIZE_KB = 896;
    // EXPECT_FILE_SIZE_KB对应字节大小
    private static final int EXPECT_FILE_SIZE_BYTE = EXPECT_FILE_SIZE_KB * 1024;

    /**
     * 临时缓存路径
     */
    private String cachePath;

    /**
     * context
     */
    private Context context;

    /**
     * 用于比对的宽
     */
    private int expectWidth;

    /**
     * 用于比对的高
     */
    private int expectHeight;

    public BitmapCompresser(Context context, int expectWidth, int expectHeight) {
        this.expectWidth = expectWidth;
        this.expectHeight = expectHeight;
        this.cachePath = PathHelper.getInstance(context, "tmp", false).getDirFilePath().getAbsolutePath();
    }

    public static BitmapCompresser newInstance(Context context,
                                               int expectWidth, int expectHeight) {
        BitmapCompresser compresser = new BitmapCompresser(context, expectWidth,
                expectHeight);
        compresser.context = context;
        return compresser;
    }

    public static BitmapCompresser getDefault(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return newInstance(context, dm.widthPixels, dm.heightPixels);
    }

    /**
     * @param photoPath 图片路径
     * @return 是否需要缩放
     */
    public synchronized boolean isNeedCompress(String photoPath) {
        File photoFile = new File(photoPath);
        long fileLength = photoFile.length();
        if (fileLength <= EXPECT_FILE_SIZE_BYTE) {
            Log.i(TAG, "#compress don't need:" + (fileLength / 1024));
            return false;
        }
        return true;
    }

    /**
     * 进行缩放
     *
     * @param photoPath 图片路径
     * @param photoName 文件名
     * @return 返回处理后的路径
     */
    public synchronized String compress(String photoPath, String photoName) {
        String compressPath = photoPath;
        File photoFile = new File(photoPath);
        long fileLength = photoFile.length();
        if (!isNeedCompress(photoPath)) {
            Log.i(TAG, "#compress don't need:" + (fileLength / 1024));
            return compressPath;
        }
        Log.e(TAG, "#compress origin size:" + (fileLength / 1024));
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        // 获取宽高
        BitmapFactory.decodeFile(photoPath, opts);
        // 计算采样率
        int inSampleSize = BitmapUtil.caculateInSampleSize(opts, expectWidth,
                expectHeight);
        Log.e(TAG, "compress inSampleSize:" + inSampleSize);
        // 可能的采样率缩小
        opts.inSampleSize = inSampleSize;
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Config.RGB_565;// 降低内存消耗
        opts.inPurgeable = true;// 设置为True时，表示系统内存不足时可以被回 收，设置为False时，表示不能被回收
        opts.inInputShareable = true;// 设置是否深拷贝，与inPurgeable结合使用

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, opts);
        // 进行压缩
        byte[] datas = BitmapUtil.compressBitmap(bitmap, EXPECT_FILE_SIZE_KB);
        // 压缩成功
        if (datas != null) {
            // 保存压缩后图片
            savePhotoToCache(datas, photoName);
            compressPath = getFilePath(photoName).toString();
        } else {
            if (inSampleSize > 1) {
                // 判断采样率，采样率改变保存压缩后图片
                savePhotoToCache(bitmap, photoName);
                compressPath = getFilePath(photoName).toString();
            }
        }
        // 释放图片内存
        recycleBitmap(bitmap);
        System.gc();
        return compressPath;
    }

    /**
     * 保存图片到缓存
     *
     * @param photoBitmap bitmap图片对象
     * @param photoName 图片名
     */
    public void savePhotoToCache(Bitmap photoBitmap, String photoName) {
        String rootPath = getRootPath();
        LogX.d("#rootPath:" + rootPath);
        File dir = new File(rootPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File photoFile = new File(rootPath, photoName);
        if (photoFile.exists()) {
            photoFile.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(photoFile);
            if (photoBitmap != null) {
                if (photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                        fileOutputStream)) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        } catch (FileNotFoundException e) {
            photoFile.delete();
            e.printStackTrace();
        } catch (IOException e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存图片数据到缓存
     *
     * @param datas 图片数据
     * @param photoName 图片名
     */
    public void savePhotoToCache(byte[] datas, String photoName) {
        String rootPath = getRootPath();
        LogX.d("#rootPath:" + rootPath);
        File dir = new File(rootPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File photoFile = new File(rootPath, photoName);
        if (photoFile.exists()) {
            photoFile.delete();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(photoFile);
            fileOutputStream.write(datas);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            photoFile.delete();
            e.printStackTrace();
        } catch (IOException e) {
            photoFile.delete();
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param fileName 文件名
     * @return 获得临时缓存文件名
     */
    public File getFilePath(String fileName) {
        String rootPath = getRootPath();
        File rootDir = new File(rootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return new File(rootPath, fileName);
    }

    /**
     * @return 缓存根目录
     */
    public String getRootPath() {
        return cachePath;
    }

    /**
     * 回收图片对象
     *
     * @param bitmap 图片对象
     */
    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

}
