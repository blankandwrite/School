package aqtc.gl.school.main.find.mvp.contract;

/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);


}
