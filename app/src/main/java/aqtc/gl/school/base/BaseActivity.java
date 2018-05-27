package aqtc.gl.school.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.okhttpwrapper.OkHttpUtil;
import com.jaeger.library.StatusBarUtil;

import aqtc.gl.school.R;
import aqtc.gl.school.widget.TitleView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author gl
 * @date 2018/5/6
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity {
    // 用来标记同一生命周期
    private String tag = "";
    public Context mContext;
    public TitleView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityViewById());
        ButterKnife.bind(this);
        setStatusBar();
        mContext = this;
        initView();
        findTitleViewId();
        setback();
    }

    public abstract int getActivityViewById();

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
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtil.getInstance(mContext).cancelTag(getTAG());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
