package aqtc.gl.school.main.find.mvp.contract;

/**
 * Created by yiwei on 16/4/1.
 */
public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);


}
