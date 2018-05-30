package aqtc.gl.school.main.home.presenter.homecommonList;


import android.content.Context;

import aqtc.gl.school.R;
import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.main.home.api.HomeApiFactory;
import aqtc.gl.school.main.home.entity.HomeCommonListEntity;
import aqtc.gl.school.utils.GsonUtil;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * @author gl
 * @date 2018/5/28
 * @desc
 */
public class HomeCommonListPresenter extends RBasePresenter<HomeCommonListContract.IomeCommonListView>
        implements HomeCommonListContract.ICommonListPresenter {


    public HomeCommonListPresenter(HomeCommonListContract.IomeCommonListView view) {
        super(view);
    }

    @Override
    public void getListData(final Context context, String tag, String schoolId, int page,
                            String categoryId, String rows) {
        addSubscription(HomeApiFactory.getHomeList(schoolId, String.valueOf(page), categoryId, rows)
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody responseBody) throws Exception {
                                   HomeCommonListEntity homeCommonListEntity = GsonUtil.jsonToBean(responseBody.string(), HomeCommonListEntity.class);
                                   if (null != homeCommonListEntity && null != homeCommonListEntity.data && null != homeCommonListEntity.data.list) {
                                       mView.onScuess(homeCommonListEntity.data.list);
                                   } else {
                                       mView.onFail(context.getResources().getString(R.string.no_data));
                                   }
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   mView.onFail(context.getResources().getString(R.string.err));
                               }
                           }

                ));

    }

}
