package aqtc.gl.school.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import aqtc.gl.school.R;


/**
 * @author gl
 * @date 2018/5/16
 * @desc shape生成工厂
 */
public class ShapeDrawableFactory {

    public static Drawable makeRectDrawable(Context context, int strokeColor, int fillColor,
                                            int radius) {
        Resources res = context.getResources();
        GradientDrawable d = (GradientDrawable) context.getResources()
                .getDrawable(R.drawable.rect_layer_bg);
        ((GradientDrawable) d.mutate()).setCornerRadius(radius);
        d.setStroke(DensityUtil.dip2px(context, 0.5F), res.getColor(strokeColor));
        d.setColor(res.getColor(fillColor));
        d.setCornerRadius(radius);
        return d;
    }

    public static Drawable makeRectDrawable(Context context, int strokeColor) {
        Resources res = context.getResources();
        return makeRectDrawable(context, strokeColor,R.color.transparent, 0);
    }
}
