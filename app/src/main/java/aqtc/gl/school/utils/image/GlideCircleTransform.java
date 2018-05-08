package aqtc.gl.school.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import aqtc.gl.school.utils.DensityUtil;


/**
 * @author gl
 * @date 2018/5/8
 * @desc
 */
public class GlideCircleTransform extends BitmapTransformation {

    private Paint mBorderPaint;
    private float mBorderWidth;
    private Context context;

    public GlideCircleTransform(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * @param context
     * @param borderWidth 头像圆环的宽度
     * @param borderColor 头像圆环的颜色
     */
    public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
        super(context);
        mBorderWidth = DensityUtil.dip2px(context, borderWidth);
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(borderWidth);
        this.context = context;
    }


    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        System.out.println("#transform:" + outWidth + " " + outHeight);
        return circleCrop(pool, toTransform, outWidth, outHeight);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
        if (source == null) return null;
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        int outSize = Math.min(outWidth, outHeight);
        int size = (int) (Math.min(outWidth, outHeight));
        int sourceSize = (int) (Math.min(sourceWidth, sourceHeight));
        int x = (sourceWidth - sourceSize) / 2;
        int y = (sourceHeight - sourceSize) / 2;

        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        //计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) size) / sourceSize;
        float scaleHeight = ((float) size) / sourceSize;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        // 必要的缩放保证fill输出图片最大宽高
        Bitmap squared = Bitmap.createBitmap(source, x, y, sourceSize, sourceSize, matrix, true);
        Bitmap result = pool.get(outSize, outSize, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(outSize, outSize, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = outSize / 2f;
        canvas.drawCircle(r, r, r - mBorderWidth / 2, paint);
        if (mBorderPaint != null) {
            float borderRadius = r;
            canvas.drawCircle(r, r, borderRadius - mBorderWidth / 2, mBorderPaint);
        }
        System.out.println("#circleCrop");
        return result;
    }

    @Override
    public String getId() {
        return "";
    }
}
