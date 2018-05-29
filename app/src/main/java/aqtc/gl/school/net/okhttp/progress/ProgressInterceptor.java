/*
 * Copyright (c) aibona 2017.
 */

package aqtc.gl.school.net.okhttp.progress;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by chenpengfei on 2016/11/9.
 */
public class ProgressInterceptor implements Interceptor {

    private ProgressCallback progressCallback;

    public ProgressInterceptor(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(), progressCallback)).build();
    }

}
