package aqtc.gl.school.main.login;

import android.content.Context;
import android.text.TextUtils;

import aqtc.gl.school.main.login.entity.LoginBean;
import aqtc.gl.school.utils.sharepreference.PreferenceHelper;


/**
 * @author gl
 * @date 2018/5/24
 * @desc 登录信息缓存
 */
public class LoginInfoCache {
    /**
     * 文件名
     */
    public static final String LOGIN_FILE_NAME = "login";
    /**
     * 登录状态
     */
    private boolean login = false;
    private static LoginInfoCache sInstance = new LoginInfoCache();
    private PreferenceHelper preferenceHelper;

    private LoginInfoCache() {
        preferenceHelper = PreferenceHelper.getInstance(LOGIN_FILE_NAME);
    }

    public static LoginInfoCache getInstance() {
        return sInstance;
    }

    /**
     * @return 是否登录
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * @param context 初始化用户登录信息
     */
    public void initLoginInfo(Context context) {
        LoginBean.DataBean account = getLoginBean(context);
        if (account != null && !TextUtils.isEmpty(account.token)) {
            login = true;
        } else {
            login = false;
            clear(context);
        }
    }
    /**
     * 清除登录信息
     * @param context
     */
    public void clear(Context context) {
        preferenceHelper.clear(context);
        login = false;
    }
    /**
     * @param context
     * @param bean 登录bean
     * 保存登录信息
     */
    public void save(Context context, LoginBean.DataBean bean) {
        preferenceHelper.save(context, LoginConstance.TOKEN, bean.token);
        preferenceHelper.save(context, LoginConstance.STUDENTCODE, bean.studentCode);
        preferenceHelper.save(context, LoginConstance.CLASSID, bean.clazzId);
        preferenceHelper.save(context, LoginConstance.CLASANAME, bean.clazzName);
        login = true;
    }
    /**
     * @param context
     * @return 登录信息
     */
    public LoginBean.DataBean getLoginBean(Context context) {
        LoginBean.DataBean bean = new LoginBean.DataBean();
        bean.token = preferenceHelper.getString(context, LoginConstance.TOKEN, "");
        bean.studentCode = preferenceHelper.getString(context, LoginConstance.STUDENTCODE, "");
        bean.clazzId = preferenceHelper.getString(context, LoginConstance.CLASSID, "");
        bean.clazzName = preferenceHelper.getString(context, LoginConstance.CLASANAME, "");
        return bean;
    }

    /**
     * @param context
     * @return token
     */
    public static String getToken(Context context) {
        return getInstance().getLoginBean(context).token;
    }

    /**
     * 保存的key
     */
    public static class LoginConstance {
        public static final String TOKEN = "TOKEN";
        public static final String STUDENTCODE = "STUDENTCODE";
        public static final String CLASSID = "CLASSID";
        public static final String CLASANAME = "CLASANAME";

    }
}
