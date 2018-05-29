package aqtc.gl.school.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import aqtc.gl.school.SchoolApplication;
import aqtc.gl.school.net.download.ProgressListener;
import aqtc.gl.school.net.download.ProgressResponseBody;
import aqtc.gl.school.utils.NetWorkUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author gl
 * @date 2018/5/29
 * @desc OkHttpClient管理类
 */
public class OkHttpManager {

    private OkHttpManager() {

    }

    public static OkHttpClient create() {
        return create(null);
    }

    public static OkHttpClient create(ProgressListener progressListener) {
        Interceptor interceptor = new HttpCacheInterceptor();
        if (progressListener != null) {
            interceptor = new HttpProgressInterceptor(progressListener);
        }
        File cacheFile = new File(SchoolApplication.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache)
                .build();
        return okHttpClient;
    }


    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(SchoolApplication.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(SchoolApplication.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    static class HttpProgressInterceptor implements Interceptor {
        private ProgressListener mProgressListener;

        public HttpProgressInterceptor(ProgressListener progressListener) {
            mProgressListener = progressListener;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse
                    .newBuilder()
                    .body(new ProgressResponseBody(originalResponse, mProgressListener))
                    .build();
        }
    }

}
