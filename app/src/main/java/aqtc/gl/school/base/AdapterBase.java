package aqtc.gl.school.base;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonRecyclerRecyclerAdapter;

import java.util.List;

/**
 * @author gl
 * @date 2018/5/22
 * @desc adapter基类
 */
public abstract class AdapterBase<T> extends CommonRecyclerRecyclerAdapter<T> {
    protected List<T> list;
    protected Context mContext;
    public AdapterBase(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
            this.list=datas;
            this.mContext = context;
    }

    public void refresh(List<T> data) {
        if (data != null) {
            if (data.size() > 0) {
                if (list==data) { // 两个list 是同一个
                    notifyDataSetChanged();
                    return;
                } else {
                    list.clear();
                    list.addAll(data);
                }
            } else {
                list.clear();
            }
        } else {
            list.clear();
        }
        notifyDataSetChanged();
    }


    /**
     * 清理List集合
     */
    public void clean() {
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加集合
     *
     * @param list
     */
    public void addAll(List<T> list) {
        if (list != null) {
            if (list.size() > 0) {
                if (this.list.equals(list)) { // 两个list 是同一个
                    notifyDataSetChanged();
                    return;
                } else {
                    this.list.addAll(list);
                }
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 添加集合到指定位置
     *
     * @param list
     * @param position
     */
    public void addAll(List<T> list, int position) {
        this.list.addAll(position, list);
        notifyDataSetChanged();
    }
    /**
     * 返回List集合
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }
}
