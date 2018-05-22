package com.android.okhttpwrapper.callback;

/**
 * @author gl
 * @date 2018/5/22
 * @desc 请求回调，返回需要的类型对象
 */
public interface RequestGetBean<T> {
    void getBean(T t);
}