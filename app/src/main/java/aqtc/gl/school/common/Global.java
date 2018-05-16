package aqtc.gl.school.common;

import android.content.Context;

import java.io.File;

import aqtc.gl.school.R;
import aqtc.gl.school.utils.file.PathHelper;

/**
 * 存放公共配置,获取公共配置
 * Created by FuJinhu on 2017-2-9.
 */
public class Global {
    public static final int ROWS = 10;
    public static final String BASE_FILE_PATH = "school";
    public static final String PATH_GLIDE = "glide";
    public static final String PATH_PIC = "image";
    public static final String PATH_VIDEO = "video";
    public static final String PATH_LOG = "log";
    public static final String PATH_FILE = "file";
    public static final String PATH_TMP = "tmp";
    public static final int FIND_PIC_SELECT_MAX_COUNT = 9;
    public static final int IMAGE_DEFAULT = R.mipmap.no_image;


    public static void initAppPath(Context context) {
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_PIC, false);
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_VIDEO, false);
    }

    public static PathHelper getPicPath(Context context) {
        return PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_PIC, false);
    }

    public static PathHelper getVideoPath(Context context) {
        return PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_VIDEO, false);
    }

    public static PathHelper getLogPath(Context context) {
        return PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_LOG, false);
    }

    public static PathHelper getFilePath(Context context) {
        return PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_FILE, false);
    }
    public static PathHelper getTmpPath(Context context) {
        return PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_TMP, false);
    }


}
