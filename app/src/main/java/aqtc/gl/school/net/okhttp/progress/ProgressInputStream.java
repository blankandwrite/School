/*
 * Copyright (c) aibona 2017.
 */

package aqtc.gl.school.net.okhttp.progress;

import java.io.IOException;
import java.io.InputStream;

/**
 * 带进度的输入流
 * Created by xmqian on 2017/7/17 0017.
 */

public class ProgressInputStream extends InputStream {
    private final InputStream stream;
    private final ProgressCallback listener;

    private long total;
    private long totalRead;


    ProgressInputStream(InputStream stream, ProgressCallback listener, long total) {
        this.stream = stream;
        this.listener = listener;
        this.total = total;
    }


    @Override
    public int read() throws IOException {
        int read = this.stream.read();
        if (this.total < 0) {
            this.listener.progress(-1, -1, false);
            return read;
        }
        if (read >= 0) {
            this.totalRead++;
            this.listener.progress(this.totalRead, this.total, read==-1);
        }
        return read;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int read = this.stream.read(b, off, len);
        if (this.total < 0) {
            this.listener.progress(-1, -1, false);
            return read;
        }
        if (read >= 0) {
            this.totalRead += read;
            this.listener.progress(this.totalRead, this.total, read==-1);
        }
        return read;
    }

    @Override
    public void close() throws IOException {
        if (this.stream != null) {
            this.stream.close();
        }
    }
}
