package com.android.okhttpwrapper.callback;

/**
 * @author fujinhu
 * @date 2017/11/30
 * @desc ui层回调
 */
public interface OnResponse<T> {
    /**
     * 请求回调到ui
     *
     * @param temp
     */
    void responseOk(T temp);

    /**
     * 请求失败的回调
     *
     * @param msg
     */
    void responseFail(String msg);
}