package aqtc.gl.school.main.find.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.utils.image.ImageLoad;


public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mList;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItemLayoutId = itemLayoutId;
        updateList(list);
    }

    public void updateList(List<T> list) {
        mList = list;
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = ViewHolder.get(mContext, convertView,
                parent, mItemLayoutId, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item);

    /**
     * 为ImageView设置图片
     *
     * @param imageView
     * @param url
     * @return
     */
    protected void setImageByUrl(ImageView imageView, String url) {
        ImageLoad.getVideoImageGlide(mContext, url).centerCrop().into(imageView);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    protected void setText(ViewHolder holder, int viewId, CharSequence text) {
        TextView textView = holder.getView(viewId);
        textView.setText(text);
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    protected void setImageResource(ViewHolder holder, int viewId, int resId) {
        ImageView imageView = holder.getView(viewId);
        imageView.setImageResource(resId);
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawable
     * @return
     */
    protected void setImageDrawable(ViewHolder holder, int viewId,
                                    Drawable drawable) {
        ImageView imageView = holder.getView(viewId);
        imageView.setImageDrawable(drawable);
    }

    public static class ViewHolder {
        private final SparseArray<View> mViews;
        private int mPosition;
        private View mConvertView;

        private ViewHolder(Context context, ViewGroup parent, int layoutId,
                           int position) {
            this.mPosition = position;
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId,
                    parent, false);
            mConvertView.setTag(this);
        }

        /**
         * 拿到一个ViewHolder对象
         *
         * @param context
         * @param convertView
         * @param parent
         * @param layoutId
         * @param position
         * @return
         */
        public static ViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId, int position) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutId, position);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.mPosition = position;
            }
            return holder;
        }

        public View getConvertView() {
            return mConvertView;
        }

        /**
         * 通过控件的Id获取对于的控件，如果没有则加入views
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public int getPosition() {
            return mPosition;
        }
    }

}
