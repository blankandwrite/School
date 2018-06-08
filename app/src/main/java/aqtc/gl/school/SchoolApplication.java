package aqtc.gl.school;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.common.preload.Preloader;
import aqtc.gl.school.main.login.LoginInfoCache;
import aqtc.gl.school.net.ApiClient;
import aqtc.gl.school.net.OkHttpManager;
import aqtc.gl.school.utils.LogX;
import okhttp3.OkHttpClient;

/**
 * @author gl
 * @date 2018/5/8
 * @desc
 */
public class SchoolApplication extends Application {


    protected static SchoolApplication mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        //初始化RetrofitClient
        initRetrofit();
        //初始化登录信息
        LoginInfoCache.getInstance().initLoginInfo(mAppContext);
        //初始化log
        LogX.initLog(mAppContext);
        //文件路径初始化
        Global.initAppPath(mAppContext);
        //网络请求临时缓存初始化
        Preloader.getInstance(mAppContext).reset();
        //友盟初始化且必须要放在主线程里
        UMConfigure.init(mAppContext,UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    public static SchoolApplication getContext(){
        if (null == mAppContext){
            mAppContext = new SchoolApplication();
        }
        return mAppContext;
    }

    /**
     * 初始化RetrofitClient
     */
    private void initRetrofit() {
        OkHttpClient okHttpClient = OkHttpManager.create();
        ApiClient.create(CommonUrl.BASE_URL, okHttpClient);
    }

}
