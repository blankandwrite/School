package aqtc.gl.school.main.home.activity.mvp.homecommondetail;

import android.content.Context;

import com.android.okhttpwrapper.OkHttpUtil;
import com.android.okhttpwrapper.callback.OnResponse;

import java.util.HashMap;
import java.util.Map;

import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;
import aqtc.gl.school.utils.GsonUtil;

/**
 * @author gl
 * @date 2018/5/28
 * @desc
 */
public class HomeCommomDatailPresenter extends RBasePresenter<HomeCommonDetailContract.HomeCommonDetailView>
implements HomeCommonDetailContract.IHomeCommonDetailPresenter{

    public HomeCommomDatailPresenter(HomeCommonDetailContract.HomeCommonDetailView view) {
        super(view);
    }

    @Override
    public void getData(Context context, String tag, String id) {
        Map<String,String> params = new HashMap<>();
        params.put("id",id);
        OkHttpUtil.getInstance(context).doRequestByPost(CommonUrl.ARTICLE__DETAIL,tag, params,
                new OnResponse<String>() {
                    @Override
                    public void responseOk(String temp) {
                        HomeCommonDetailEntity homeCommonDetailEntity = GsonUtil.jsonToBean(temp,HomeCommonDetailEntity.class);
                        if (null != homeCommonDetailEntity && null != homeCommonDetailEntity.data){
                             mView.onSucess(homeCommonDetailEntity.data);
                        }
                    }

                    @Override
                    public void responseFail(String msg) {
                        mView.onFail(msg);
                    }
                });
    }
}
