package aqtc.gl.school.common.preload;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aqtc.gl.school.net.okhttp.OkHttpUtil;
import aqtc.gl.school.net.okhttp.callback.OnResponse;

/**
 * @author gl
 * @date 2018/5/24
 * @desc 数据预加载类
 */
public class Preloader<T> {
    /**
     * 单例
     */
    private static Preloader sInstance = new Preloader();

    /**
     * 数据缓存
     */
    private HashMap<String, Object> caches = new HashMap();

    public Context context;

    public static Preloader getInstance(Context context) {
        sInstance.context = context;
        return sInstance;
    }

    /**
     * 预加载数据
     *
     * @param context
     * @param url 请求地址
     * @param tag 标签
     * @param map 参数
     * @param callback 预加载缓存
     */
    public void loadData(final Context context, String url, final String tag, Map<String, String> map, final PreloaderCallback<T> callback) {
        List<T> latestResult = (List<T>) caches.get(tag);
        if (latestResult != null && !latestResult.isEmpty()) {
            callback.getResult(latestResult);
        } else {
            doLoad(context, url, tag, map, callback);
        }
    }

    /**
     * @param context
     * @param url 请求地址
     * @param tag 标签
     * @param map 参数
     * @param callback 预加载缓存
     */
    public void doLoad(final Context context, String url, final String tag, Map<String, String> map, final PreloaderCallback<T> callback) {
        OkHttpUtil.getInstance(context).doRequestByPost(url, tag, map, new OnResponse<String>() {
            @Override
            public void responseOk(String temp) {
                List<T> result = callback.parseResult(temp);
                caches.put(tag, result);
                callback.getResult(result);
            }

            @Override
            public void responseFail(String msg) {
                callback.fail(msg);
            }
        });
    }


    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancel(String tag) {
        OkHttpUtil.getInstance(context).cancelTag(tag);
    }

    /**
     * 数据重置
     */
    public void reset() {
        caches.clear();
    }


}
