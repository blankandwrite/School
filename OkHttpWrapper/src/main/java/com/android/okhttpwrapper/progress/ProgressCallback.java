/*
 * Copyright (c) aibona 2017.
 */

package com.android.okhttpwrapper.progress;

/**
 * http://www.jianshu.com/p/548031c62257
 */
public interface ProgressCallback {
    /**
     * 进度发生了改变，如果numBytes，totalBytes，percent都为-1，则表示总大小获取不到
     *
     * @param bytesRead 已读/写大小
     * @param contentLength 总大小
     * @param done 是否完成
     */
    void progress(long bytesRead, long contentLength, boolean done);

    void onProgressChanged(long totalWritten, long total, float percent);
}
