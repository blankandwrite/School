package aqtc.gl.school.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * @author gl
 * @date 2018/5/30
 * @desc
 */
public abstract class RBaseFragment<T extends RBasePresenter> extends Fragment {
    protected Bundle savedInstanceState;
    private boolean isPrepare;
    private boolean isVisible;
    protected T mPresenter;
    private boolean isFirstInit;
    protected BaseActivity mContext;
    protected LayoutInflater inflater;
    // 视图化的对象
    protected View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inflater = LayoutInflater.from(context);
        try {
            mContext = (BaseActivity) context;
        } catch (Exception e) {
            throw new IllegalArgumentException("这个fragment的父activity必须继承BaseActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        mRootView = inflater.inflate(setFragmentLayoutRes(), container, false);
        ButterKnife.bind(this, mRootView);
        mPresenter = getPresenter();
        isPrepare = true;
        isVisible = true;
        isFirstInit = true;
        onVisible();
        return mRootView;
    }

    /**
     * 在这里实现Fragment数据的懒加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 懒加载，触发调用数据封装层
     */
    private void onVisible() {
        if (!isPrepare || !isVisible) {
            return;
        }
        if (isFirstInit) {
            initView(savedInstanceState, mRootView);
            isFirstInit = false;
        } else {
            onRepeatVisible();

        }
    }

    /**
     * 重复可见调用方法
     */
    private void onRepeatVisible() {

    }

    /**
     * 不可见的时候
     */
    private void onInvisible() {

    }


    /**
     * 设置当前Fragment的布局文件资源
     *
     * @return
     */
    protected abstract int setFragmentLayoutRes();

    /**
     * 初始化页面
     *
     * @param savedInstanceState
     */
    public abstract void initView(Bundle savedInstanceState, View rootView);

    protected abstract T getPresenter();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
