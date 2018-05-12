package com.android.okhttpwrapper;

import android.content.Context;
import android.os.Handler;

import com.android.okhttpwrapper.callback.OnResponse;
import com.library.log.LogX;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author fujinhu
 * @date 2017/11/30
 * @desc Okhttp工具类
 */
public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";

    /**
     * okhttp client
     */
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //读取超时
        clientBuilder.readTimeout(20, TimeUnit.SECONDS);
        //连接超时
        clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        //写入超时
        clientBuilder.writeTimeout(2 * 60, TimeUnit.SECONDS);
        //心跳频率
        clientBuilder.pingInterval(10, TimeUnit.SECONDS);
        mOkHttpClient = clientBuilder.build();
    }

    /**
     * Context
     */
    private Context context;
    /**
     * 用于主线程处理异常回调
     */
    private Handler mHandler;

    private OkHttpUtil(Context context) {
        this.context = context;
        //Looper.getMainLooper()
        this.mHandler = new Handler(context.getMainLooper());
    }

    public static OkHttpUtil getInstance(Context context) {
        OkHttpUtil okHttpUtil = new OkHttpUtil(context);
        return okHttpUtil;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


    /**
     * 开启异步线程访问网络
     *
     * @param url 地址
     * @param tag 标签
     * @param params
     * @param onResponse
     */
    public void doRequestByPost(String url, String tag, Map<String, String> params, final OnResponse<String> onResponse) {
        doRequest(url, tag, params, OkHttpRequest.Method.POST, onResponse);
    }

    /**
     * 开启异步线程访问网络
     *
     * @param url 地址
     * @param tag 标签
     * @param params 参数
     * @param method 请求方式
     * @param onResponse 回调
     */
    public void doRequest(String url, String tag, final Map<String, String> params, int method, final OnResponse<String> onResponse) {
        OkHttpRequest okHttpRequest = new OkHttpRequest(method);
        okHttpRequest.putParams(params);
        Request.Builder requestBuild = new Request.Builder()
                .url(url)
                .tag(tag);
        if (okHttpRequest.isPost()) {
            requestBuild.post(okHttpRequest.getRequestBody());
        } else {
            requestBuild.get();
        }
        //打印地址日志
        LogX.e(TAG, "#mapParams:" + params + "\n#url:" + url);
        mOkHttpClient.newCall(requestBuild.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogX.e("#onFailure:" + e);
                if (call.isCanceled()) {
                    //如果是主动取消的情况下
                    onError(onResponse, "");
                } else {
                    //其他情况下
                    onError(onResponse, "请求超时，请重试");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseStr = response.body().string();
                    final JSONObject jsonObject = new JSONObject(responseStr);
                    LogX.e(TAG, "#response:" + jsonObject.toString());
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        onSuccess(onResponse, responseStr);
                    } else {
                        final String error = jsonObject.getString("msg");
                        onError(onResponse, error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onError(onResponse, "访问失败，请重试");
                }
            }
        });
    }

    /**
     * 失败回调
     *
     * @param onResponse
     * @param error
     */
    private void onError(final OnResponse onResponse, final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (onResponse != null) {
                    onResponse.responseFail(error);
                }
            }
        });
    }

    /**
     * 成功回调
     *
     * @param onResponse
     * @param responseStr
     */
    private void onSuccess(final OnResponse onResponse, final String responseStr) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (onResponse != null) {
                    onResponse.responseOk(responseStr);
                }
            }
        });
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public void cancelTag(Object tag) {
        LogX.d("#cancelTag:" + tag);
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mOkHttpClient.dispatcher().cancelAll();
    }
}
