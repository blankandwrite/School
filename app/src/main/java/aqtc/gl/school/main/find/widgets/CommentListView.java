package aqtc.gl.school.main.find.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.bean.CircleItemServer;
import aqtc.gl.school.main.find.spannable.CircleMovementMethod;
import aqtc.gl.school.main.find.spannable.SpannableClickable;
import aqtc.gl.school.main.find.utils.UrlUtils;
import aqtc.gl.school.utils.image.ImageLoad;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public class CommentListView extends LinearLayout {
    private int itemColor;
    private int itemSelectorColor;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private List<CircleItemServer.ListBean.CommentListBean> mDatas;
    private LayoutInflater layoutInflater;
    private int type = 0;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDatas(List<CircleItemServer.ListBean.CommentListBean> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = datas;
        notifyDataSetChanged();
    }

    public List<CircleItemServer.ListBean.CommentListBean> getDatas() {
        return mDatas;
    }

    public CommentListView(Context context) {
        super(context);
        this.context = context;
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        this.context = context;
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PraiseListView, 0, 0);
        try {
            //textview的默认颜色
            itemColor = typedArray.getColor(R.styleable.PraiseListView_item_color, getResources().getColor(R.color.praise_item_default));
            itemSelectorColor = typedArray.getColor(R.styleable.PraiseListView_item_selector_color, getResources().getColor(R.color.praise_item_selector_default));

        } finally {
            typedArray.recycle();
        }
    }

    public void notifyDataSetChanged() {

        removeAllViews();
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int position) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(getContext());
        }
        View convertView = null;
        final CircleItemServer.ListBean.CommentListBean bean = mDatas.get(position);
        if (type == 0) {
            convertView = layoutInflater.inflate(R.layout.find_item_comment, null, false);
            fillList(convertView, bean, position);
        } else {
            convertView = layoutInflater.inflate(R.layout.find_item_comment_detail, null, false);
            fillDetail(convertView, bean, position);
        }

        return convertView;
    }

    private void fillList(View convertView, CircleItemServer.ListBean.CommentListBean bean, final int position) {
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);

        String name = bean.getUser().getName();
        String id = bean.id;
        String toReplyName = "";
        //0为对动态进行评论1为对人进行评论
        if (bean.type == 0) {
            toReplyName = "";
        } else if (bean.type == 1) {
            toReplyName = bean.getToReplyUser() == null ? "" : bean.getToReplyUser().getName();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, bean.getUser().getId()));

        if (!TextUtils.isEmpty(toReplyName)) {
            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName, bean.getToReplyUser().getId()));
        }
        builder.append(": ");
        //转换表情字符
        String contentBodyStr = bean.content;
        builder.append(UrlUtils.formatUrlString(contentBodyStr));
        commentTv.setText(builder);

        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            }
        });
        commentTv.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void fillDetail(View convertView, CircleItemServer.ListBean.CommentListBean bean, final int position) {
        ImageView indicatorImg = (ImageView) convertView.findViewById(R.id.indicatorImg);
        if (position == 0) {
            indicatorImg.setVisibility(View.VISIBLE);
        } else {
            indicatorImg.setVisibility(View.INVISIBLE);
        }
        ImageView userImg = (ImageView) convertView.findViewById(R.id.userImg);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        final CircleMovementMethod circleMovementMethod = new CircleMovementMethod(itemSelectorColor, itemSelectorColor);

    //    ImageLoad.getHeadGlide(context, bean.getUser().getHeadImage(), userImg);
        ImageLoad.loadRoundImage(context,bean.getUser().getHeadUrl(),R.mipmap.ic_user,userImg);
        String name = bean.getUser().getName();
        String id = bean.id;
        String toReplyName = "";
        //0为对动态进行评论1为对人进行评论
        if (bean.type == 0) {
            toReplyName = "";
        } else if (bean.type == 1) {
            toReplyName = bean.getToReplyUser() == null ? "" : bean.getToReplyUser().getName();
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name, bean.getUser().getId()));

        if (!TextUtils.isEmpty(toReplyName)) {
            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName, bean.getToReplyUser().getId()));
        }
        builder.append("\n");
        //转换表情字符
        String contentBodyStr = bean.content;
        builder.append(UrlUtils.formatUrlString(contentBodyStr));
        commentTv.setText(builder);

        commentTv.setMovementMethod(circleMovementMethod);
        commentTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            }
        });
        commentTv.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (circleMovementMethod.isPassToTv()) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @NonNull
    private SpannableString setClickableSpan(final String textStr, final String id) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(itemColor) {
                                    @Override
                                    public void onClick(View widget) {
                              //          PersonDetailActivity.openPersonDetailNew(context, id);
                                        //Toast.makeText(getContext(), textStr + " &id = " + id, Toast.LENGTH_SHORT).show();
                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }

    public static interface OnItemClickListener {
        public void onItemClick(int position);
    }

    public static interface OnItemLongClickListener {
        public void onItemLongClick(int position);
    }




}
