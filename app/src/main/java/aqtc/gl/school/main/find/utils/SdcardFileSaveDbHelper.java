package aqtc.gl.school.main.find.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 多媒体数据入系统库
 * sd卡主动扫描保存：http://blog.csdn.net/chendong_/article/details/52290329
 */
public class SdcardFileSaveDbHelper {

    /**
     * 插入video,针对非系统资源文件夹保存video操作
     *
     * @param context    上下文
     * @param filePath   文件路径
     * @param createTime 创建时间
     * @param duration   时长，必须是毫秒级别
     */
    public static void insertVideoToMediaStore(Context context, String filePath, long createTime, long duration) {
        if (!checkFile(filePath))
            return;
        if (createTime == 0)
            createTime = System.currentTimeMillis();
        File saveFile = new File(filePath);
        ContentValues values = initCommonContentValues(saveFile);
        values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, createTime);
        values.put(MediaStore.Video.VideoColumns.DURATION, duration);
        // video/*
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 插入image,针对非系统资源文件夹保存图片操作
     *
     * @param context    上下文
     * @param filePath   文件路径
     * @param createTime 创建时间
     */
    public static void insertImageToMediaStore(Context context, String filePath, long createTime) {
        if (!checkFile(filePath))
            return;
        if (createTime == 0)
            createTime = System.currentTimeMillis();
        File saveFile = new File(filePath);
        ContentValues values = initCommonContentValues(saveFile);
        values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, createTime);
        values.put(MediaStore.Images.ImageColumns.ORIENTATION, 0);
        // image/*
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 针对非系统文件夹下的文件,使用该方法
     * 插入时初始化公共字段
     *
     * @param saveFile 文件
     * @return ContentValues
     */
    public static ContentValues initCommonContentValues(File saveFile) {
        ContentValues values = new ContentValues();
        long currentTimeInSeconds = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATE_ADDED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length());
        return values;
    }

    // 监测文件
    public static boolean checkFile(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera"
     * 针对系统文夹只需要扫描|||不然会重复!!!
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public static void scanIntoMediaStore(Context context, String filePath) {
        if (!checkFile(filePath))
            return;
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(intent);
    }
}
