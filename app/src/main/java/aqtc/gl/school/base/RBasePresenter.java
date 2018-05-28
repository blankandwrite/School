package aqtc.gl.school.base;

/**
 * Created by a on 2017/5/15.
 */

public class RBasePresenter<T extends IRBaseView> implements IRBasePresenter {

    protected T mView;

    public RBasePresenter(T view) {
        mView = view;
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void onStop() {

    }


}
