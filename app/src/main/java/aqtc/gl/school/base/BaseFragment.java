package aqtc.gl.school.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 基类Fragment
 */
public abstract class BaseFragment extends Fragment {
    // 用来标记同一生命周期
    private String tag = "";
    // 视图化的对象
    protected View rootView;
    protected Context mContext;
    protected LayoutInflater inflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (rootView = inflater.inflate(getFragmentViewId(), container, false));
    }


    public abstract void initView(View rootView);

    public abstract int getFragmentViewId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initView(view);
    }

    public String getTAG() {
        tag = getClass().getSimpleName();
        return tag;
    }


}
