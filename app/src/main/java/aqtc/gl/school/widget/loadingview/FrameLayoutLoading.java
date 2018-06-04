package aqtc.gl.school.widget.loadingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import aqtc.gl.school.R;


/**
 * @author gl
 * @date 2018/6/4
 * @desc 加载的view
 */
public class FrameLayoutLoading extends FrameLayout {

    private LayoutInflater mInflater;
    //加载失败
    public static final int ViewType_ERROR = -1;
    //正在加载
    public static final int ViewType_Loading = 0;

    private RefreashClickListener mRefreshClickListener;

    private SparseIntArray defaultLayout = new SparseIntArray(2);
    public SparseArray<View> cachedLayout = new SparseArray<View>(2);

    public FrameLayoutLoading(@NonNull Context context) {
        this(context, null);
    }

    public FrameLayoutLoading(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameLayoutLoading(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode())
            return;
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FrameLayoutLoading);
            if (a != null) {
                setDefaultView(ViewType_Loading, a.getResourceId(R.styleable.FrameLayoutLoading_loadingView,
                        R.layout.common_loading_indicator));

                setDefaultView(ViewType_ERROR, a.getResourceId(R.styleable.FrameLayoutLoading_errorView,
                        R.layout.common_loading_error));

                a.recycle();
            }
        }
    }


    public void setDefaultView(int viewType, int resLayout) {
        defaultLayout.put(viewType, resLayout);
    }


    public void showView(int viewType) {
        int count = defaultLayout.size();
        for (int i = 0; i < count; i++) {
            int key = defaultLayout.keyAt(i);
            if (key == viewType) {
                try {
                    doShowView(key);
                } catch (Exception e) {
                    System.gc();
                }
            } else {
                hideView(key);
                System.gc();
            }
        }
    }


    private void doShowView(int viewType) {
        int resLayoutId = defaultLayout.get(viewType);
        if (resLayoutId <= 0)
            throw new IllegalStateException("layout is not set for " + viewType);

        View view = cachedLayout.get(viewType);

        if (view == null) {
            view = mInflater.inflate(resLayoutId, null);
            cachedLayout.put(viewType, view);
            addView(view, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
        initListener(view);
        view.setVisibility(VISIBLE);
        view.bringToFront();
    }

    private void hideView(int viewType) {
        View view = cachedLayout.get(viewType);
        if (view == null)
            return;

        view.setVisibility(GONE);
    }

    public void showLoadingView() {
        showView(ViewType_Loading);
    }

    public void showErrorView() {
        showView(ViewType_ERROR);
    }

    public void hideView() {
        View loadingView = cachedLayout.get(ViewType_Loading);
        if (loadingView != null) {
            loadingView.setVisibility(GONE);
        }
        View errorView = cachedLayout.get(ViewType_ERROR);
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }

    }


    private void initListener(View view) {
        View refreshBtn = view.findViewById(R.id.loading_refreash_btn);
        if (refreshBtn != null) {
            refreshBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mRefreshClickListener) {
                        mRefreshClickListener.setRefresh();
                    }
                }
            });
        }
    }

    public interface RefreashClickListener {
        void setRefresh();
    }

    public void setRefreashClickListener(RefreashClickListener listener) {
        mRefreshClickListener = listener;
    }
}
