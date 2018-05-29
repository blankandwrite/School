
/*
 * Copyright (c) aibona 2017.
 */
package aqtc.gl.school.net.okhttp.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;


public class ProgressRequestBody extends RequestBody {
    private final RequestBody mRequestBody;
    private final ProgressCallback progressListener;


    public ProgressRequestBody(RequestBody requestBody, ProgressCallback progressListener) {
        this.mRequestBody = requestBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (progressListener == null) {
            mRequestBody.writeTo(sink);
            return;
        }
        ProgressOutputStream progressOutputStream = new ProgressOutputStream(sink.outputStream(), progressListener, contentLength());
        BufferedSink progressSink = Okio.buffer(Okio.sink(progressOutputStream));
        mRequestBody.writeTo(progressSink);
        progressSink.flush();
    }

}
