package com.android.okhttpwrapper;

import android.util.Log;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.RequestBody;

/**
 * @author gl
 * @date 2018/5/22
 * @desc  OKHttp请求数据包装
 */
public class OkHttpRequest {
    /**
     * 编码
     */
    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 数据集合
     */
    private Map<String, String> params = new LinkedHashMap<>();
    /**
     * 默认post请求
     */
    private int method = Method.POST;

    public OkHttpRequest(int method) {
        this.method = method;
    }

    /**
     * 设置参数
     *
     * @param key
     * @param value
     * @return
     */
    public OkHttpRequest putParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    /**
     * 设置参数
     *
     * @param params
     * @return
     */
    public OkHttpRequest putParams(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
        return this;
    }

    /**
     * 是否为post请求
     *
     * @return
     */
    public boolean isPost() {
        return method == Method.POST;
    }

    /**
     * post请求参数
     *
     * @return
     */
    public RequestBody getRequestBody() {
        RequestBody body = null;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        Iterator<String> iterator = params.keySet().iterator();
        String key = "";
        while (iterator.hasNext()) {
            key = iterator.next().toString();
            if (params.get(key) == null) {
                continue;
            }
            formEncodingBuilder.add(key, params.get(key));
            Log.d("post http", "post_Params===" + key + "====" + params.get(key));
        }
        body = formEncodingBuilder.build();
        return body;
    }

    /**
     * @return 获得get拼接数据
     */
    public String getGetBody() {
        Set<Map.Entry<String, String>> datas = params.entrySet();
        StringBuilder sb = new StringBuilder();
        String value = "";
        for (Map.Entry<String, String> data : datas) {
            value = data.getValue();
            try {
                value = URLEncoder.encode(value, CHARSET_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sb.append(data.getKey() + "=" + value);
            sb.append("&");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }


    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
    }
}
