package com.android.okhttpwrapper.callback;

/**
 * @author gl
 * @date 2018/5/22
 * @descv ui层回调
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