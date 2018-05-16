package aqtc.gl.school.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import aqtc.gl.school.common.Global;

/**
 * @author gl
 * @date 2018/5/8
 * @desc Glide图片使用
 */
public class ImageLoad {

    /**
     * 正常加载图片，没有任何修饰操作，自己设置默认图片
     *
     * @param context
     * @param url
     * @param iv
     * @param ResourceId
     */
    public static <T> void loadImage(Context context, T url, ImageView iv, int ResourceId) {
        Glide.with(context)
                .load(url)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .placeholder(ResourceId)
                .error(ResourceId)
                .fallback(ResourceId)
                .into(iv);
    }

    public static <T> void loadImageFit(Context context, T url, ImageView iv, int ResourceId) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(ResourceId)
                .error(ResourceId)
                .fallback(ResourceId)
                .into(iv);
    }


    /**
     * 设定固定高度的图片
     * @param context
     * @param url
     * @param iv
     * @param width
     * @param height
     */
    public static void loadResizeImage(Context context, String url, ImageView iv, int resId, int width, int height) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .placeholder(resId)
                .error(resId)
                .into(iv);
    }


    /**
     * 设置圆形图片
     *
     * @param context
     * @param url
     * @param iv
     */
    public static void loadRoundImage(Context context, String url, int resId, ImageView iv) {
        Glide.with(context).load(url).error(resId).placeholder(resId).transform(new GlideCircleTransform(context)).into(iv);
    }


    /**
     * 配置头像默认图片
     * BitmapTransformation不生效说明->http://stackoverflow.com/questions/30768505/bitmaptransformation-in-the-glide-library-not-working-as-expected
     *
     * @param context
     * @param url
     * @return
     */
    public static <T> BitmapRequestBuilder<T, Bitmap> getHeadGlide(Context context, T url, int resId, int borderWidth, int borderColor) {
        return Glide.with(context).load(url).asBitmap().centerCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resId).error(resId).fallback(resId).transform(createCircleTransform(context, borderWidth, borderColor));
    }

    public static <T> void getHeadGlide(final Context context, T url, int resId, final ImageView imageView) {
        Glide.with(context).load(url).asBitmap().centerCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resId).error(resId).fallback(resId).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static <T> BitmapRequestBuilder<T, Bitmap> getImageGlide(Context context, T url, int resId) {
        return Glide.with(context).load(url).asBitmap().skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(resId).error(resId).fallback(resId);
    }


    /**
     * 留边框专用
     *
     * @param context
     * @param borderWidth
     * @param borderColor
     * @return
     */
    public static BitmapTransformation createCircleTransform(Context context, int borderWidth, int borderColor) {
        Resources resources = context.getResources();
        return new GlideCircleTransform(context, borderWidth, resources.getColor(borderColor));
    }

    /**
     * 配置一般图片默认加载图片
     *
     * @param context
     * @param url
     * @return
     */
    public static <T> DrawableRequestBuilder<T> getVideoImageGlide(Context context, T url) {
        return getVideoImageGlide(context, url, Global.IMAGE_DEFAULT);
    }

    public static <T> DrawableRequestBuilder<T> getVideoImageGlide(Context context, T url, int resId) {
        return Glide.with(context).load(url).placeholder(resId).error(resId).fallback(resId);
    }
}
