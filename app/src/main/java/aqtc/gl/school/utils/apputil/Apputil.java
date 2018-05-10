package aqtc.gl.school.utils.apputil;

import android.content.Context;
import android.content.Intent;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class Apputil {

    public static void goActivity(Context context,Class<?> clazz){
        context.startActivity(new Intent(context,clazz));
    }
}
