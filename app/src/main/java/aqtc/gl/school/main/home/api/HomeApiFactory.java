package aqtc.gl.school.main.home.api;

import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import aqtc.gl.school.net.ApiClient;
import aqtc.gl.school.net.RxSchedulers;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author gl
 * @date 2018/5/30
 * @desc
 */
public class HomeApiFactory {

    public static Observable<HomeCommonDetailEntity> getDetail(String id) {
        return ApiClient.get(CommonUrl.BASE_URL)
                .create(HomeService.class)
                .getHomeListDetail(id)
                .compose(RxSchedulers.<HomeCommonDetailEntity>ioMain());
    }

    public static Observable<ResponseBody> getHomeList(String schoolId,String page,String categoryId,String rows) {
        return ApiClient.get(CommonUrl.BASE_URL)
                .create(HomeService.class)
                .getHomeList(schoolId,page,categoryId,rows)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }
}
