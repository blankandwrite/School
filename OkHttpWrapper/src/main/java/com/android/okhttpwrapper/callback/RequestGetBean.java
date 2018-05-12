package com.android.okhttpwrapper.callback;

/**请求回调，返回需要的类型对象
 * @param <T>
 */
public interface RequestGetBean<T> {
    void getBean(T t);
}