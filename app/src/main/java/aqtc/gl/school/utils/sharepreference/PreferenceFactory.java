package aqtc.gl.school.utils.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceFactory {
    private static PreferenceFactory sInstance = new PreferenceFactory();
    private Context mContext;

    private PreferenceFactory() {

    }

    public static PreferenceFactory getInstance(Context context) {
        sInstance.mContext = context;
        return sInstance;
    }

    public SharedPreferences createSharedPreferences(String name) {
        return createSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SharedPreferences createSharedPreferences(String name, int mode) {
        SharedPreferences sp = sInstance.mContext.getSharedPreferences(name,
                mode);
        return sp;
    }

}
