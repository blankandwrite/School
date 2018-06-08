package aqtc.gl.school.main.home.api;

import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author gl
 * @date 2018/5/30
 * @desc
 */

public interface HomeService {
    /**
     * 获取列表条目
     * @return
     */
    @POST(CommonUrl.ARTICLE_LIS)
    Observable<ResponseBody> getHomeList(@Query("school_id") String school_id,@Query("page") String page,
                                         @Query("category_id") String category_id,  @Query("limit") String limit );

    /**
     * 获取列表条目详情
     * @return
     */
    @POST(CommonUrl.ARTICLE_DETAIL)
    Observable<HomeCommonDetailEntity> getHomeListDetail(@Query("id") String id);
}
