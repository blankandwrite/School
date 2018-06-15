package aqtc.gl.school.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;

import aqtc.gl.school.common.CommonUrl;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 通用工具类
 */

public class Utils {
    /**
     * 返回屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    /**
     * 检测Sdcard是否存在
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }


    /**
     * 隐藏软件盘
     * @param context
     */
    public static void hideSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null)
                    inputMethodManager.hideSoftInputFromWindow(activity
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * @param context
     * @return 返回版本号
     */
    public static String getVersionName(Context context) {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * @param context
     * @return versionCode
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionCode = -1;
        }
        return versionCode;
    }

    /**
     * dip转px的工具的方法
     *
     * @param dpValue dip的数值
     * @param context 上下文的对象
     * @return 转换后的px
     */
    public static int getPxByDp(int dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 返回当前屏幕是否为竖屏。
     *
     * @param context
     * @return 当且仅当当前屏幕为竖屏时返回true, 否则返回false。
     */
    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


}
