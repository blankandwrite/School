package aqtc.gl.school.main.find.adapter.viewholder;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.widgets.CommentListView;
import aqtc.gl.school.main.find.widgets.ExpandTextView;
import aqtc.gl.school.main.find.widgets.PraiseListView;
import aqtc.gl.school.main.find.widgets.SnsPopupWindow;
import aqtc.gl.school.main.find.widgets.videolist.model.VideoLoadMvpView;
import aqtc.gl.school.main.find.widgets.videolist.widget.TextureVideoView;
import aqtc.gl.school.widget.PraiseLayout;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public abstract class CircleViewHolder extends RecyclerView.ViewHolder implements VideoLoadMvpView {

    public int viewType;

    public ImageView headIv;
    public TextView nameTv;
    public TextView urlTipTv;
    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /* 点赞列表 */
    public PraiseListView praiseListView;
    /* 评论详情专用，显示头像 */
    public PraiseLayout praiseLayout;

    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        initSubView(viewType, viewStub);

        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        digLine = itemView.findViewById(R.id.lin_dig);

        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);
        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);

        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);
        praiseLayout = (PraiseLayout) itemView.findViewById(R.id.praiseLayout);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);

        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

    @Override
    public TextureVideoView getVideoView() {
        return null;
    }

    @Override
    public void videoBeginning() {

    }

    @Override
    public void videoStopped() {

    }

    @Override
    public void videoPrepared(MediaPlayer player) {

    }

    @Override
    public void videoResourceReady(String videoPath) {

    }

    public void setOnClickListener(View.OnClickListener listener){

    }
}
