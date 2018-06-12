package aqtc.gl.school.main.find.mvp.presenter;

import android.content.Context;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import aqtc.gl.school.common.CommonUrl;
import aqtc.gl.school.main.find.bean.CircleItemServer;
import aqtc.gl.school.main.find.bean.CommentConfig;
import aqtc.gl.school.main.find.bean.User;
import aqtc.gl.school.main.find.listener.IDataRequestListener;
import aqtc.gl.school.main.find.mvp.contract.CircleContract;
import aqtc.gl.school.main.find.mvp.modle.CircleModel;
import aqtc.gl.school.main.login.LoginInfoCache;
import aqtc.gl.school.net.okhttp.OkHttpUtil;
import aqtc.gl.school.net.okhttp.callback.OnResponse;
import aqtc.gl.school.utils.ToastUtils;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter implements CircleContract.Presenter {
	private CircleModel circleModel;
	private CircleContract.View view;
	private Context context;
	private String tag;

	public CirclePresenter(CircleContract.View view) {
		this.circleModel = new CircleModel();
		this.view = view;
	}

	public void setServer(Context context, String tag) {
		this.context = context;
		this.tag = tag;
	}

	/**
	 * @param item
	 * @return void    返回类型
	 * @throws
	 * @Title: deleteCircle
	 * @Description: 删除动态
	 */
	public void deleteCircle(final CircleItemServer.ListBean item) {
		circleModel.deleteCircle(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				if (view != null) {
					view.update2DeleteCircle(item);
				}
			}
		});
	}

	/**
	 * @param circlePosition
	 * @return void    返回类型
	 * @throws
	 * @Title: addFavort
	 * @Description: 点赞
	 */
	public void addFavort(final int circlePosition, String dynamicId) {
		if (view != null) {
			view.update2AddFavorite(circlePosition, dynamicId);
		}
	}

	/**
	 * @param @param circlePosition
	 * @param @param favortId
	 * @return void    返回类型
	 * @throws
	 * @Title: deleteFavort
	 * @Description: 取消点赞
	 */
	public void deleteFavort(final int circlePosition, final String favortId) {
		if (view != null) {
			view.update2DeleteFavort(circlePosition, favortId);
		}
	}

	/**
	 * @param content
	 * @param config CommentConfig
	 * @return void    返回类型
	 * @throws
	 * @Title: addComment
	 * @Description: 增加评论
	 */
	public void addComment(final String content, final CommentConfig config) {
		if (config == null) {
			return;
		}
		Map<String, String> params = new HashMap<>();
		params.put("content", content);
		CircleItemServer.ListBean dynamic = config.dynamic;
		if (config.commentType == CommentConfig.Type.PUBLIC) {
			//发布人的数据
			User publishUser = config.dynamic.getUser();
			params.put("targetid", publishUser.getId());
			//0动态
			params.put("type", "0");
		} else if (config.commentType == CommentConfig.Type.REPLY) {
			//带过来的数据
			User replyUser = config.replyUser;
			params.put("targetid", replyUser.getId());
			//1回复
			params.put("type", "1");
		}
		params.put("dynamicid", dynamic.getId());
		params.put("token", LoginInfoCache.getInstance().getLoginBean(context).token);

		OkHttpUtil.getInstance(context).doRequestByPost(CommonUrl.FIND_ADD_COMMENT, tag, params, new OnResponse<String>() {
			@Override
			public void responseOk(String temp) {
				CircleItemServer.ListBean.CommentListBean newItem = null;
				aqtc.gl.school.main.login.entity.User myUser = LoginInfoCache.getMyUser();
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					newItem = new CircleItemServer.ListBean.CommentListBean();
					newItem.setUserId(myUser.id);
					newItem.setName(myUser.name);
					newItem.setType(0);
					newItem.setHeadImage(myUser.headImage);
					//发布人的数据
					User publishUser = config.dynamic.getUser();
					newItem.setTargetId(publishUser.getId());
					newItem.setTargetName(publishUser.getName());
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					newItem = new CircleItemServer.ListBean.CommentListBean();
					newItem.setUserId(myUser.id);
					newItem.setName(myUser.name);
					newItem.setType(1);
					newItem.setHeadImage(myUser.headImage);
					//带过来的数据
					User replyUser = config.replyUser;
					newItem.setTargetId(replyUser.getId());
					newItem.setTargetName(replyUser.getName());
				}
				newItem.setContent(content);
				newItem.setHeadImage(myUser.headImage);
				if (view != null) {
					view.update2AddComment(config.circlePosition, newItem);
				}
			}

			@Override
			public void responseFail(String msg) {
				if (view != null) {
					view.update2AddComment(config.circlePosition, null);
				}
				ToastUtils.showMsg(context, msg);
			}
		});

	}

	/**
	 * @param @param circlePosition
	 * @param @param commentId
	 * @return void    返回类型
	 * @throws
	 * @Title: deleteComment
	 * @Description: 删除评论
	 */
	public void deleteComment(final int circlePosition, final String commentId) {
		circleModel.deleteComment(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				if (view != null) {
					view.update2DeleteComment(circlePosition, commentId);
				}
			}

		});
	}

	/**
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig) {
		if (view != null) {
			view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
		}
	}


	/**
	 * 清除对外部对象的引用，反正内存泄露。
	 */
	public void recycle() {
		this.view = null;
	}
}