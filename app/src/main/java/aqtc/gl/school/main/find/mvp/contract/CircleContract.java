package aqtc.gl.school.main.find.mvp.contract;


import java.util.List;

import aqtc.gl.school.main.find.bean.CircleItemServer;
import aqtc.gl.school.main.find.bean.CommentConfig;

/**动态视图接口，动态操作接口
 * Created by suneee on 2016/7/15.
 */
public interface CircleContract {

    interface View extends BaseView{
        void update2DeleteCircle(CircleItemServer.ListBean circleItem);
        void update2AddFavorite(int circlePosition, String dynamicId);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CircleItemServer.ListBean.CommentListBean addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<CircleItemServer.ListBean> datas);
        void loadData(int loadType);
    }

    interface Presenter extends BasePresenter{
        void deleteCircle(final CircleItemServer.ListBean circleItem);
        void addFavort(final int circlePosition, String dynamicId);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);

    }
}
