package aqtc.gl.school.main.find.mvp.contract;


import java.util.List;

import aqtc.gl.school.main.find.bean.CircleItem;
import aqtc.gl.school.main.find.bean.CommentConfig;
import aqtc.gl.school.main.find.bean.CommentItem;
import aqtc.gl.school.main.find.bean.FavortItem;

/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public interface CircleContract {

    interface View extends BaseView{
        void update2DeleteCircle(String circleId);
        void update2AddFavorite(int circlePosition, FavortItem addItem);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CommentItem addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<CircleItem> datas);
    }

    interface Presenter extends BasePresenter{
        void loadData(int loadType);
        void deleteCircle(final String circleId);
        void addFavort(final int circlePosition);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);

    }
}
