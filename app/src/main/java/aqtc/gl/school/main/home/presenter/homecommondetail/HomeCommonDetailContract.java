package aqtc.gl.school.main.home.presenter.homecommondetail;

import android.content.Context;

import aqtc.gl.school.base.IRBaseView;
import aqtc.gl.school.main.home.entity.HomeCommonDetailEntity;

/**
 * @author gl
 * @date 2018/5/28
 * @desc
 */
public class HomeCommonDetailContract {

   public interface HomeCommonDetailView extends IRBaseView{
        //处理数据
        void onSucess(HomeCommonDetailEntity.DataBean dataBean);
        void onFail(String err);

    }

   public interface IHomeCommonDetailPresenter {
       //获取数据
       void getData(Context context, String tag, String id);
   }

}
