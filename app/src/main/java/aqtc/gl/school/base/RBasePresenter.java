package aqtc.gl.school.base;

/**
 * @author gl
 * @date 2018/5/28
 * @desc
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
