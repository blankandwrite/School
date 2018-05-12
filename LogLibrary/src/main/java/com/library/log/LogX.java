package com.library.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 日志工具类
 */
public class LogX {
    private static final String TAG = "LogX";
    private static boolean isOpen = true;
    private static boolean isLogError = isOpen;
    private static boolean isDebug = isOpen;
    private static boolean isLogFile = isOpen;
    private static boolean isLogTime = isOpen;
    private static boolean isLogPrint = isOpen;
    private static boolean isLogSystem = isOpen;

    public static void initLog(Context context) {
        isOpen = isApkDebugable(context);
        isLogError = isOpen;
        isDebug = true;
        isLogFile = isOpen;
        isLogTime = isOpen;
        isLogPrint = isOpen;
        isLogSystem = isOpen;
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (isLogError) {
            Log.e(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }


    public static void println(String msg) {
        if (isLogSystem) {
            System.out.println(msg);
        }
    }

    public static void time(String tag, String msg) {
        if (isLogTime) {
            Log.i(tag, msg + " : " + System.currentTimeMillis());
        }
    }

    public static void logFile(String fileName, String msg) {
        if (isLogFile) {
            d(fileName, msg);
            File fileDir = new File(Environment.getExternalStorageDirectory(),
                    "/mlogs/");
            File logFile = new File(fileDir, fileName);

            FileWriter fileOutputStream = null;
            try {
                if (!fileDir.exists()) {
                    if (!fileDir.mkdirs()) {
                        return;
                    }
                }
                if (!logFile.exists()) {
                    if (!logFile.createNewFile()) {
                        return;
                    }
                }
                fileOutputStream = new FileWriter(logFile, true);
                fileOutputStream.write(msg);
                fileOutputStream.flush();
            } catch (Exception e) {
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    public static void printStackTrace(Exception e) {
        if (isLogPrint) {
            e.printStackTrace();
        }
    }

    /**
     * 是否为debug
     *
     * @param context
     * @return
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
