package aqtc.gl.school.main.find.mvp.presenter;

import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import aqtc.gl.school.main.find.bean.CircleItem;
import aqtc.gl.school.main.find.bean.CommentConfig;
import aqtc.gl.school.main.find.bean.CommentItem;
import aqtc.gl.school.main.find.bean.FavortItem;
import aqtc.gl.school.main.find.listener.IDataRequestListener;
import aqtc.gl.school.main.find.mvp.contract.CircleContract;
import aqtc.gl.school.main.find.mvp.modle.CircleModel;
import aqtc.gl.school.main.find.utils.DatasUtil;
import aqtc.gl.school.utils.LogX;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter implements CircleContract.Presenter{
	private CircleModel circleModel;
	private CircleContract.View view;

	public CirclePresenter(CircleContract.View view){
		circleModel = new CircleModel();
		this.view = view;
	}

	public void loadData(int loadType){

        List<CircleItem> datas = DatasUtil.createCircleDatas();
        if(view!=null){
            view.update2loadData(loadType, datas);
			LogX.e("json",new Gson().toJson(datas.get(0)));
        }
	}


	/**
	 * 
	* @Title: deleteCircle 
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCircle(final String circleId){
		circleModel.deleteCircle(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteCircle(circleId);
                }
			}
		});
	}
	/**
	 * 
	* @Title: addFavort 
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavort(final int circlePosition){
		circleModel.addFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				FavortItem item = DatasUtil.createCurUserFavortItem();
                if(view !=null ){
                    view.update2AddFavorite(circlePosition, item);
                }

			}
		});
	}
	/**
	 * 
	* @Title: deleteFavort 
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavort(final int circlePosition, final String favortId){
		circleModel.deleteFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view !=null ){
                    view.update2DeleteFavort(circlePosition, favortId);
                }
			}
		});
	}
	
	/**
	 * 
	* @Title: addComment 
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		circleModel.addComment(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem newItem = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					newItem = DatasUtil.createPublicComment(content);
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					newItem = DatasUtil.createReplyComment(config.replyUser, content);
				}
                if(view!=null){
                    view.update2AddComment(config.circlePosition, newItem);
                }
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteComment 
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteComment(final int circlePosition, final String commentId){
		circleModel.deleteComment(new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteComment(circlePosition, commentId);
                }
			}
			
		});
	}

	/**
	 *
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
        if(view != null){
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
	}


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}
