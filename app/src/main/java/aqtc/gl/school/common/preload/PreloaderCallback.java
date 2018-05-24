package aqtc.gl.school.common.preload;

import java.util.List;

/**
 * @author gl
 * @date 2018/5/24
 * @desc
 */

public interface PreloaderCallback<T> {
    /**
     * 解析数据
     *
     * @param json json数据
     * @return 返回数据列表
     */
    List<T> parseResult(String json);

    /**
     * @param result result数据
     */
    void getResult(List<T> result);

    /**
     * 预加载失败
     *
     * @param errorMsg 异常数据
     */
    void fail(String errorMsg);
}
