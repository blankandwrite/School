package aqtc.gl.school.common;

/**
 * @author gl
 * @date 2018/5/22
 * @desc url
 */
public class CommonUrl {
    //www.xiangline.com  regular  http://106.14.8.195:8800/
    public static final String BASE_URL_TEST = "http://test-api.xiangline.com/";
    public static final String BASE_URL_REGULAR = "http://api.xiangline.com/";

    public static final String BASE_URL = BASE_URL_TEST;
    //首页
    public static final String ARTICLE_CATEGORY = BASE_URL + "api/v1/app/article/categories";
    public static final String ARTICLE_LIS = "api/v1/app/article/pages";
    public static final String ARTICLE_DETAIL = "api/v1/app/article/detail";

    //模拟动态列表
    public static final String FIND_DYNAMIC_LIST_URL = "http://quality.snzo.cn/sztx/open/discovery/dynamic/list";
    //新增评论
    public static final String FIND_ADD_COMMENT = BASE_URL + "/open/discovery/dynamic/comment";
    //朋友圈
}
