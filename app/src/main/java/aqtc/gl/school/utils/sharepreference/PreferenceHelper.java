package aqtc.gl.school.utils.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * @author gl
 * @date 2018/5/24
 * @desc SharePreference工具类
 */
public class PreferenceHelper {
    public static final String FILE_NAME = "app_config";
    private String mFileName;

    private PreferenceHelper() {
        this(FILE_NAME);
    }

    private PreferenceHelper(String fileName) {
        this.mFileName = fileName;
    }

    public static PreferenceHelper getDefault() {
        return new PreferenceHelper();
    }

    public static PreferenceHelper getInstance(String fileName) {
        return new PreferenceHelper(fileName);
    }

    public boolean checkKeyExists(String key, Context context) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        return sp.contains(key);
    }

    /**
     * @param key
     * @param value
     */
    public void save(Context context, String key, int value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = PreferenceFactory.getInstance(context)
                    .createSharedPreferences(mFileName);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(key, value);
            editor.commit();
        }

    }

    /**
     * @param key
     * @param context
     * @param defaultValue
     * @return
     */
    public int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        return sp.getInt(key, defaultValue);
    }

    /**
     * @param key
     * @return 默认0
     */
    public int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    /**
     * @param key
     * @param value
     */
    public void save(Context context, String key, boolean value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = PreferenceFactory.getInstance(context)
                    .createSharedPreferences(mFileName);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

    }

    /**
     * @param key
     * @return默认false
     */
    public boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * @param key
     * @param value
     */
    public void save(Context context, String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = PreferenceFactory.getInstance(context)
                    .createSharedPreferences(mFileName);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    /**
     * @param key
     * @return默认null
     */
    public String getString(Context context, String key) {
        return getString(context, key, "");
    }

    /**
     * @param key
     * @param context
     * @param defaultValue
     * @return
     */
    public String getString(Context context, String key,
                            String defaultValue) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        return sp.getString(key, defaultValue);
    }

    /**
     * @param key
     * @param value
     */
    public void save(Context context, String key, float value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = PreferenceFactory.getInstance(context)
                    .createSharedPreferences(mFileName);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat(key, value);
            editor.commit();
        }
    }

    /**
     * @param key
     * @return 默认值0
     */
    public float getFloat(Context context, String key) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        return sp.getFloat(key, 0);
    }

    /**
     * @param key
     * @param value
     */
    public void save(Context context, String key, long value) {
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = PreferenceFactory.getInstance(context)
                    .createSharedPreferences(mFileName);
            SharedPreferences.Editor editor = sp.edit();
            editor.putLong(key, value);
            editor.commit();
        }
    }

    /**
     * @param key
     * @return 默认0
     */
    public long getLong(String key, Context context) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        return sp.getLong(key, 0);
    }

    public void remove(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear(Context context) {
        SharedPreferences sp = PreferenceFactory.getInstance(context)
                .createSharedPreferences(mFileName);
        sp.edit().clear().commit();
    }
}
