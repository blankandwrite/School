package aqtc.gl.school.utils.apputil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import aqtc.gl.school.main.login.LoginActivity;
import aqtc.gl.school.main.login.LoginInfoCache;

/**
 * @author gl
 * @date 2018/5/10
 * @desc 该APP工具
 */
public class Apputil {

    public static void goActivity(Context context,Class<?> clazz){
        context.startActivity(new Intent(context,clazz));
    }

    /**
     * 一键登录
     */
    public static boolean checkLogin(Object obj) {
        //没有登录信息跳转登录页面
        if (!LoginInfoCache.getInstance().isLogin()) {
            if (obj instanceof Fragment) {
                Fragment frg = (Fragment) obj;
                Intent intent = new Intent(frg.getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                frg.startActivity(intent);
            } else if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                Intent intent = new Intent(activity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
            return false;
        }
        return true;
    }



}
