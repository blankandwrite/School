package aqtc.gl.school.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import aqtc.gl.school.widget.TitleView;
import butterknife.ButterKnife;

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
        mContext = this;
        ButterKnife.bind(this);
        initView();
        findTitleViewId();
        setback();
    }

    public abstract int getActivityViewById();

    public abstract void initView();

    /**
     * 获取头部标题
     * 注意：TitleView不为空 所有有关头部的操作要放在该方法里操作
     */
    public abstract void findTitleViewId();

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

}
