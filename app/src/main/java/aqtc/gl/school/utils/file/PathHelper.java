package aqtc.gl.school.utils.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author gl
 * @date 2018/5/14
 * @desc 文件路径工具类
 */
public class PathHelper {
    private static final String TAG = "PathHelper";
    private String separator = File.separator;
    private Context mContext;
    private String dirPath;
    private boolean isPublic;

    public static PathHelper getInstance(Context context, String pathName, boolean isPublic) {
        return new PathHelper(context, pathName, isPublic);
    }


    public PathHelper(Context context, String pathName, boolean isPublic) {
        this.mContext = context;
        this.isPublic = isPublic;
        if (TextUtils.isEmpty(pathName)) {
            pathName = "tmp";
        }
        this.dirPath = makeDirs(pathName);
    }

    public String getDirPath() {
        return dirPath;
    }

    public File getDirFilePath() {
        return new File(dirPath);
    }

    public String makeDirs(String pathName) {
        String dirPath = "";
        Context context = mContext.getApplicationContext();
        File file = null;
        if (isSdcardExists()) {
            file = new File(Environment.getExternalStorageDirectory(), pathName);
        } else {
            file = new File(context.getFilesDir(), isPublic ? "" : pathName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        dirPath = file.getAbsolutePath() + separator;
        Log.d(TAG, "#dir_path:" + dirPath);
        return dirPath;
    }

    public File makeFilePath(String fileName) {
        String filePath = "";
        File file = null;
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        file = new File(dirPath, fileName);
        filePath = file.getAbsolutePath();
        Log.d(TAG, "#file_path:" + filePath);
        return file;
    }

    public static boolean isSdcardExists() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public FileOutputStream makeOutputStream(Context context, String fileName) {
        FileOutputStream fos = null;
        try {
            if (isSdcardExists()) {
                File file = makeFilePath(fileName);
                fos = new FileOutputStream(file);
            } else {
                fos = context.openFileOutput(fileName,
                        Context.MODE_WORLD_READABLE);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fos;
    }
}
