package aqtc.gl.school;

import android.app.Application;
import android.content.Context;

import aqtc.gl.school.common.Global;
import aqtc.gl.school.common.preload.Preloader;
import aqtc.gl.school.main.login.LoginInfoCache;

/**
 * @author gl
 * @date 2018/5/8
 * @desc
 */
public class SchoolApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化登录信息
        LoginInfoCache.getInstance().initLoginInfo(mContext);
        //文件路径初始化
        Global.initAppPath(this);
        //网络请求临时缓存初始化
        Preloader.getInstance(mContext).reset();

    }

    public static Context getContext(){
        return mContext;
    }

}
