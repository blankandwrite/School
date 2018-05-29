package aqtc.gl.school.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;

import aqtc.gl.school.R;
import aqtc.gl.school.net.okhttp.OkHttpUtil;
import aqtc.gl.school.widget.TitleView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author gl
 * @date 2018/5/6
 * @desc
 */
public abstract class BaseActivity <T extends RBasePresenter> extends AppCompatActivity {
    // 用来标记同一生命周期
    private String tag = "";
    public Context mContext;
    public TitleView mTitleView;
    protected T mPresenter;
    protected CompositeDisposable compositeDisposable; //管理事件订阅
    protected ArrayMap<String, Disposable> disposableMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityViewById());
        ButterKnife.bind(this);
        setStatusBar();
        mContext = this;
        mPresenter = getPresenter();
        initView();
        findTitleViewId();
        setback();
    }

    /**
     * 获取布局layoutId
     * @return
     */
    public abstract int getActivityViewById();
    /**
     * 设置 Presenter
     */
    protected abstract T getPresenter();

    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 获取头部标题 操作头部子类需重写
     * 注意：TitleView不为空 所有有关头部的操作要放在该方法里操作
     */
    public void findTitleViewId() {

    }

    
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 返回监听
     */
    public void setback() {
        if (null != mTitleView) {
            mTitleView.setBackListener(new TitleView.BackListener() {
                @Override
                public void backClick() {
                    finish();
                }
            });
        }

    }


    public String getTAG() {
        try {
            if ("".equals(tag)) {
                tag = getClass().getSimpleName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    /**
     * 添加事件监听处理到 事件管理类
     *
     * @param disposable 上流事件
     */
    protected void addSubscription(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 添加事件监听处理到 事件管理类
     *
     * @param tag        标识符
     * @param disposable 上流事件
     */
    protected void addSubscription(String tag, Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        if (disposableMap == null) {
            disposableMap = new ArrayMap<>();
        }
        disposableMap.put(tag, disposable);
        compositeDisposable.add(disposable);
    }


    /**
     * RxJava取消注册，避免内存泄露
     * 取消以后就只能重新新建一个了
     */
    protected void onUnsubscribe() {
        if (compositeDisposable != null) {
            // Using clear will clear all, but can accept new disposable
//            compositeDisposable.clear();
            // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        if (disposableMap != null && disposableMap.size() > 0) {
            disposableMap.clear();
        }
    }

    /**
     * 根据标识符移除Disposable
     *
     * @param tags 标识符
     */
    protected void removeDisposableByTag(String... tags) {
        if (disposableMap != null && disposableMap.size() > 0) {
            for (String tag : tags) {
                if (disposableMap.containsKey(tag)) {
                    compositeDisposable.remove(disposableMap.get(tag));
                    disposableMap.remove(tag);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        OkHttpUtil.getInstance(mContext).cancelTag(getTAG());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
