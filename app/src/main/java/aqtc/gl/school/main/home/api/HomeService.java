package aqtc.gl.school.main.home.api;

import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by a on 2017/5/16.
 */

public interface HomeService {
    /**
     * 获取列表条目
     * @return
     */

    @POST("api/v1/app/article/pages")
    Observable<ResponseBody> getHomeList(@Query("school_id") String school_id,@Query("page") String page,
                                         @Query("category_id") String category_id,  @Query("limit") String limit );

    /**
     * 获取列表条目详情
     * @return
     */
    @POST("api/v1/app/article/detail")
    Observable<HomeCommonDetailEntity> getHomeListDetail(@Query("id") String id);
}
