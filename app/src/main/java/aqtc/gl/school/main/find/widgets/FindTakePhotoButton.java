package aqtc.gl.school.main.find.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import aqtc.gl.school.R;


@SuppressLint("AppCompatCustomView")
public class FindTakePhotoButton extends ImageView {
    private static final int PADDING = 5;
    private Context context;
    private Paint circlePaint;
    private Paint innerPaint;
    private Paint paint;
    private float mStart = 270;
    private float sweepInc = 0;
    private float mSweep;
    private Drawable bgDrawable;
    private boolean autoUpdate = false;

    private RefreshHandler mRedrawHandler = new RefreshHandler();

    public FindTakePhotoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources res = context.getResources();
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setDither(true);
        paint.setColor(res.getColor(R.color.blue3));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setDither(true);
        circlePaint.setColor(res.getColor(R.color.gray8));

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setDither(true);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(res.getColor(R.color.white));

        Drawable background = getBackground();
        //background包括color和Drawable,这里分开取值
        if (background instanceof ColorDrawable) {
            ColorDrawable colordDrawable = (ColorDrawable) background;
            int color = colordDrawable.getColor();
            circlePaint.setColor(color);
            setBackgroundDrawable(null);
        } else {
            bgDrawable = background;
            setBackgroundDrawable(null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopUpdate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (paint == null)
            return;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG));
        clear(canvas);
        drawCircle(canvas);
        drawArcs(canvas);
        if (autoUpdate) {
            update();
        }
    }

    public void setMaxSecond(int maxSecond) {
        sweepInc = 360 / maxSecond;
        sweepInc = sweepInc <= 0 ? 0 : sweepInc;
        sweepInc = sweepInc / 20.0f;
    }

    public void autoUpdate() {
        autoUpdate = true;
        postInvalidate();
    }

    public void stopUpdate() {
        autoUpdate = false;
        mRedrawHandler.removeMessages(0);
    }

    private void clear(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        Paint p = new Paint();
        //清屏
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(p);
    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        int center = getWidth() / 2;
        // 第一种方法绘制圆环
        if (bgDrawable != null) {
            bgDrawable.setBounds(PADDING, PADDING, getWidth() - PADDING, getHeight() - PADDING);
            bgDrawable.draw(canvas);
        } else {
            canvas.drawCircle(center, center, center - PADDING, circlePaint);
        }
        canvas.drawCircle(center, center, center / 2, innerPaint);
        canvas.restore();
    }

    private void drawArcs(Canvas canvas) {
        canvas.save();
        int strokeWidth = 10;
        int space = strokeWidth / 2 + PADDING - 1;
        RectF oval = new RectF(space, space, getWidth() - space, getHeight() - space);
        paint.setStrokeWidth(10);
        canvas.drawArc(oval, mStart, mSweep, false, paint);
        mSweep += sweepInc;
        if (mSweep >= 360) {
            autoUpdate = false;
        }
        canvas.restore();
    }

    private void reset() {
        mSweep = 270;
    }


    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            FindTakePhotoButton.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    ;


    public void update() {
        mRedrawHandler.sleep(50);
    }
}
