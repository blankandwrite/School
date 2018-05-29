package aqtc.gl.school.main.home.activity.mvp.homecommondetail;

import android.content.Context;

import com.library.log.LogX;

import aqtc.gl.school.R;
import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.main.home.api.HomeApiFactory;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author gl
 * @date 2018/5/28
 * @desc
 */
public class HomeCommomDatailPresenter extends RBasePresenter<HomeCommonDetailContract.HomeCommonDetailView>
        implements HomeCommonDetailContract.IHomeCommonDetailPresenter {

    public HomeCommomDatailPresenter(HomeCommonDetailContract.HomeCommonDetailView view) {
        super(view);
    }

    @Override
    public void getData(final Context context, String tag, String id) {
        addSubscription(HomeApiFactory.getDetail(id)
                .subscribe(new Consumer<HomeCommonDetailEntity>() {
                    @Override
                    public void accept(@NonNull HomeCommonDetailEntity homeCommonDetailEntity) throws Exception {
                        mView.onSucess(homeCommonDetailEntity.data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogX.e("Throwable",throwable.getMessage()+"");
                        mView.onFail(context.getResources().getString(R.string.no_data));
                    }
                }));

       /*  Map<String, String> params = new HashMap<>();
        params.put("id", id);
       OkHttpUtil.getInstance(context).doRequestByPost(CommonUrl.ARTICLE__DETAIL, tag, params,
                new OnResponse<String>() {
                    @Override
                    public void responseOk(String temp) {
                        HomeCommonDetailEntity homeCommonDetailEntity = GsonUtil.jsonToBean(temp, HomeCommonDetailEntity.class);
                        if (null != homeCommonDetailEntity && null != homeCommonDetailEntity.data) {
                            mView.onSucess(homeCommonDetailEntity.data);
                        }
                    }

                    @Override
                    public void responseFail(String msg) {
                        mView.onFail(msg);
                    }
                });*/
    }
}
