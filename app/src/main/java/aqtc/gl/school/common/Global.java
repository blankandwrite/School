package aqtc.gl.school.common;

import android.content.Context;

import java.io.File;

import aqtc.gl.school.R;
import aqtc.gl.school.utils.file.PathHelper;

/**
 * @author gl
 * @date 2018/5/18
 * @desc 存放公共配置,获取公共配置
 */
public class Global {

    public static final String BASE_FILE_PATH = "school";
    public static final String PATH_PIC = "image";
    public static final String PATH_VIDEO = "video";
    public static final String PATH_LOG = "log";
    public static final String PATH_FILE = "file";
    public static final String PATH_TMP = "tmp";
    public static final int FIND_PIC_SELECT_MAX_COUNT = 9;
    public static final int IMAGE_DEFAULT = R.mipmap.no_image;
    public static final String ROWS = "20";
    public static final int PHOTO_REQUEST_CAREMA=100;
    public static final int SELECT_PHOTO=101;
    public static final int PERMSSION_CODE=102;
    public static final int QUIT_CODE=103;


   /*{"err":0,"msg":"ok","data":[
    {"id":1,"school_id":1,"cover":"","name":"师大要闻"},
    {"id":2,"school_id":1,"cover":"","name":"校园传真"},
    {"id":3,"school_id":1,"cover":"","name":"学术动态"},
    {"id":4,"school_id":1,"cover":"","name":"媒体师大"}
    ]}*/
    public static final String SCHOOL_ID="1";
    public static final String NEWS_ID="1";
    public static final String FAX_ID="2";
    public static final String SCIENCE_ID="3";
    public static final String MEDIA_ID="4";

    public static final String TOKEN="eyJ1c2VyaWQiOiJmZjgwODA4MTVkZGY4ODhkMDE1ZGRmODhlYTA0MDAwMCIsInN0a2giOiI5NTEzMDAwMTEwMzg1NDgyIiwicmVhbE5hbWUiOiLmm7nlsI/kuq4iLCJyb2xlcyI6IlvlrabnlJ9dIiwidGFncyI6Ils5NTEzMDAwMTEwMzg1NDgyLCBBTExdIiwiempoIjoiMzQyOTAxMTk4NTA5MDg0NjE3Iiwic2V4Ijoi55S3In0=";

    public static void initAppPath(Context context) {
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_PIC, false);
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_VIDEO, false);
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_LOG, false);
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_FILE, false);
        PathHelper.getInstance(context, BASE_FILE_PATH + File.separator + PATH_TMP, false);
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
