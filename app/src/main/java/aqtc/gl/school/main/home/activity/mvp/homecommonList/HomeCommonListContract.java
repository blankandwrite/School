package aqtc.gl.school.main.home.activity.mvp.homecommonList;

import android.content.Context;

import java.util.List;

import aqtc.gl.school.base.IRBaseView;
import aqtc.gl.school.main.home.entity.HomeCommonListEntity;

/**
 * @author gl
 * @date 2018/5/28
 * @desc
 */
public class HomeCommonListContract {

    public interface IomeCommonListView extends IRBaseView {
       //处理数据
        void onScuess(List<HomeCommonListEntity.DataBean.ListBean> listBeans);
        void onFail(String err);
    }

    public interface ICommonListPresenter {
        //获取数据
        void getListData(Context context, String tag,String schoolId, int page, String categoryId, String rows);

    }
}
