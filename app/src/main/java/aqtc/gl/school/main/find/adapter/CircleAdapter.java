package aqtc.gl.school.main.find.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.common.FindCameraType;
import aqtc.gl.school.main.find.activity.ImagePagerActivity;
import aqtc.gl.school.main.find.activity.SimpleVideoPlayActivity;
import aqtc.gl.school.main.find.adapter.viewholder.CircleViewHolder;
import aqtc.gl.school.main.find.adapter.viewholder.ImageViewHolder;
import aqtc.gl.school.main.find.adapter.viewholder.URLViewHolder;
import aqtc.gl.school.main.find.adapter.viewholder.VideoViewHolder;
import aqtc.gl.school.main.find.bean.ActionItem;
import aqtc.gl.school.main.find.bean.CircleItemServer;
import aqtc.gl.school.main.find.bean.CommentConfig;
import aqtc.gl.school.main.find.mvp.presenter.CirclePresenter;
import aqtc.gl.school.main.find.utils.UrlUtils;
import aqtc.gl.school.main.find.widgets.CommentListView;
import aqtc.gl.school.main.find.widgets.ExpandTextView;
import aqtc.gl.school.main.find.widgets.MultiImageView;
import aqtc.gl.school.main.find.widgets.PraiseListView;
import aqtc.gl.school.main.find.widgets.SnsPopupWindow;
import aqtc.gl.school.main.find.widgets.dialog.CommentDialog;
import aqtc.gl.school.main.login.LoginInfoCache;
import aqtc.gl.school.utils.TimeUtils;
import aqtc.gl.school.utils.image.ImageLoad;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public class CircleAdapter extends BaseRecycleViewAdapter {

    public final static int TYPE_HEAD = -2;
    public final static int TYPE_HEAD_NEW_MESSAGE = -3;

    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;
    private int videoState = STATE_IDLE;
    public static final int HEADVIEW_SIZE = 2;

    private CirclePresenter presenter;
    private Context context;
    private View headView;
    private View newMessageView;

    public void setCirclePresenter(CirclePresenter presenter) {
        this.presenter = presenter;
    }

    public CircleAdapter(Context context) {
        this.context = context;
        headView = LayoutInflater.from(context).inflate(R.layout.find_head_circle, null, false);
        //新消息
        newMessageView = LayoutInflater.from(context).inflate(R.layout.find_new_message, null, false);
        ViewGroup.LayoutParams layoutParams = newMessageView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        newMessageView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && HEADVIEW_SIZE > 0) {
            return TYPE_HEAD;
        } else if (position == 1 && HEADVIEW_SIZE > 0) {
            return TYPE_HEAD_NEW_MESSAGE;
        } else {
            CircleItemServer.ListBean item = (CircleItemServer.ListBean) datas.get(position - HEADVIEW_SIZE);
            return item.type;
        }
    }

    public View getNewMessageView() {
        return newMessageView;
    }

    public View getHeaderView() {
        return headView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_HEAD) {
            viewHolder = new HeaderViewHolder(headView);
        } else if (viewType == TYPE_HEAD_NEW_MESSAGE) {
            viewHolder = new HeaderViewHolder(newMessageView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_circle_item, parent, false);
            if (viewType == FindCameraType.TYPE_URL) {
                viewHolder = new URLViewHolder(view);
            } else if (viewType == FindCameraType.TYPE_IMG) {
                viewHolder = new ImageViewHolder(view);
            } else if (viewType == FindCameraType.TYPE_VIDEO) {
                viewHolder = new VideoViewHolder(view);
            }
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        } else if (getItemViewType(position) == TYPE_HEAD_NEW_MESSAGE) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        } else {
            final int circlePosition = position - HEADVIEW_SIZE;
            final CircleViewHolder holder = (CircleViewHolder) viewHolder;
            final CircleItemServer.ListBean circleItem = (CircleItemServer.ListBean) datas.get(circlePosition);
            final String circleId = circleItem.id;
            String name = circleItem.name;
            String headImg = circleItem.headImage;
            final String content = circleItem.content;
            String createTime = circleItem.time;
            final List<CircleItemServer.ListBean.LikeListBean> favortDatas = circleItem.likeList;
            final List<CircleItemServer.ListBean.CommentListBean> commentsDatas = circleItem.commentList;
            boolean hasFavort = circleItem.hasFavort();
            boolean hasComment = circleItem.hasComment();

            // 头像
         //   ImageLoad.getHeadGlide(context, headImg, holder.headIv);
            ImageLoad.loadRoundImage(context,"",R.mipmap.ic_user,holder.headIv);
            holder.headIv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //AlbumMainActivity.openAlbum(context, circleItem.getUser());

                }
            });

            holder.nameTv.setText(name);
            String nowTime = TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            holder.timeTv.setText(TimeUtils.getWechatTimeBetween(nowTime, createTime));

            if (!TextUtils.isEmpty(content)) {
                holder.contentTv.setExpand(circleItem.isExpand);
                holder.contentTv.setExpandStatusListener(new ExpandTextView.ExpandStatusListener() {
                    @Override
                    public void statusChange(boolean isExpand) {
                        circleItem.isExpand = isExpand;
                    }
                });

                holder.contentTv.setText(UrlUtils.formatUrlString(content));
            }
            holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

            if (LoginInfoCache.getMyUser().id.equals(circleItem.userId)) {
                holder.deleteBtn.setVisibility(View.VISIBLE);
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
            }
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                    if (presenter != null) {
                        presenter.deleteCircle(circleItem);
                    }
                }
            });
            if (hasFavort || hasComment) {
                if (hasFavort) {//处理点赞列表
                    holder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            String userName = favortDatas.get(position).name;
                            String userId = favortDatas.get(position).userId;
                            //Toast.makeText(RobotApplication.getInstance(), userName + " &id = " + userId, Toast.LENGTH_SHORT).show();
                        //    PersonDetailActivity.openPersonDetailNew(context, userId);
                        }
                    });
                    holder.praiseListView.setDatas(favortDatas);
                    holder.praiseListView.setVisibility(View.VISIBLE);
                } else {
                    holder.praiseListView.setVisibility(View.GONE);
                }

                if (hasComment) {//处理评论列表
                    holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int commentPosition) {
                            CircleItemServer.ListBean.CommentListBean commentItem = commentsDatas.get(commentPosition);
                            if (LoginInfoCache.getMyUser().id.equals(commentItem.userId)) {//复制或者删除自己的评论
                                CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
                                //dialog.show();
                            } else {//回复别人的评论
                                if (presenter != null) {
                                    CommentConfig config = new CommentConfig();
                                    config.circlePosition = circlePosition;
                                    config.commentPosition = commentPosition;
                                    config.commentType = CommentConfig.Type.REPLY;
                                    config.replyUser = commentItem.getUser();
                                    config.dynamic = circleItem;
                                    presenter.showEditTextBody(config);
                                }
                            }
                        }
                    });
                    holder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int commentPosition) {
                            //长按进行复制或者删除
                            CircleItemServer.ListBean.CommentListBean commentItem = commentsDatas.get(commentPosition);
                            CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
                            //dialog.show();
                        }
                    });
                    holder.commentList.setDatas(commentsDatas);
                    holder.commentList.setVisibility(View.VISIBLE);

                } else {
                    holder.commentList.setVisibility(View.GONE);
                }
                holder.digCommentBody.setVisibility(View.VISIBLE);
            } else {
                holder.digCommentBody.setVisibility(View.GONE);
            }

            holder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

            final SnsPopupWindow snsPopupWindow = holder.snsPopupWindow;
            //判断是否已点赞
            String curUserFavortId = circleItem.getCurUserFavortId(LoginInfoCache.getMyUser().id);
            if (!TextUtils.isEmpty(curUserFavortId)) {
                snsPopupWindow.getmActionItems().get(0).mTitle = "取消";
            } else {
                snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
            }
            snsPopupWindow.update();
            snsPopupWindow.setmItemClickListener(new PopupItemClickListener(circlePosition, circleItem, curUserFavortId, presenter));
            holder.snsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //弹出popupwindow
                    snsPopupWindow.showPopupWindow(view);
                }
            });

            holder.urlTipTv.setVisibility(View.GONE);
            switch (holder.viewType) {
                case FindCameraType.TYPE_URL:// 处理链接动态的链接内容和和图片
                    if (holder instanceof URLViewHolder) {
                        String linkImg = circleItem.getLinkImg();
                        String linkTitle = circleItem.getLinkTitle();
                        ImageLoad.getImageGlide(context, linkImg, R.mipmap.ic_url_default).centerCrop().into(((URLViewHolder) holder).urlImageIv);
                        ((URLViewHolder) holder).urlContentTv.setText(linkTitle);
                        ((URLViewHolder) holder).urlBody.setVisibility(View.VISIBLE);
                        //((URLViewHolder) holder).urlTipTv.setVisibility(View.VISIBLE);
                        holder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //SimpleWebViewActivity.openUrl(context, circleItem.getLinkTitle(), circleItem.getLinkUrl());
                            }
                        });
                    }
                    break;
                case FindCameraType.TYPE_IMG:// 处理图片
                    if (holder instanceof ImageViewHolder) {
                        final List<CircleItemServer.ListBean.PathListBean> photos = circleItem.getPathList();
                        if (photos != null && photos.size() > 0) {
                            ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                            ((ImageViewHolder) holder).multiImageView.setList(photos);
                            ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //imagesize是作为loading时的图片size
                                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                                    List<String> photoUrls = new ArrayList<String>();
                                    for (CircleItemServer.ListBean.PathListBean photoInfo : photos) {
                                        photoUrls.add(photoInfo.getOriginal());
                                    }
                                    List<String> thumbnails = new ArrayList<String>();
                                    for (CircleItemServer.ListBean.PathListBean photoInfo : photos) {
                                        thumbnails.add(photoInfo.getThumbnail());
                                    }
                                    ImagePagerActivity.startImagePagerActivity(context, photoUrls, position, imageSize);
                                }
                            });
                        } else {
                            ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
                        }
                    }

                    break;
                case FindCameraType.TYPE_VIDEO:
                    if (holder instanceof VideoViewHolder) {
                        ((VideoViewHolder) holder).videoView.setVideoImgUrl(circleItem.getVideoBean());//视频封面图片
                        ((VideoViewHolder) holder).videoView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SimpleVideoPlayActivity.openVideo(context, circleItem.getVideoImgUrl(), circleItem.getVideoUrl());
                            }
                        });
                    }

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + HEADVIEW_SIZE;//有head需要加1
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        private String mFavorId;
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;
        private CircleItemServer.ListBean mCircleItem;
        private CirclePresenter presenter;

        public PopupItemClickListener(int circlePosition, CircleItemServer.ListBean circleItem, String favorId, CirclePresenter presenter) {
            this.mFavorId = favorId;
            this.mCirclePosition = circlePosition;
            this.mCircleItem = circleItem;
            this.presenter = presenter;
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                    if (presenter != null) {
                        if ("赞".equals(actionitem.mTitle.toString())) {
                            presenter.addFavort(mCirclePosition, mCircleItem.id);
                        } else {//取消点赞
                            presenter.deleteFavort(mCirclePosition, mFavorId);
                        }
                    }
                    break;
                case 1://发布评论
                    if (presenter != null) {
                        CommentConfig config = new CommentConfig();
                        config.circlePosition = mCirclePosition;
                        config.dynamic = mCircleItem;
                        config.commentType = CommentConfig.Type.PUBLIC;
                        presenter.showEditTextBody(config);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
