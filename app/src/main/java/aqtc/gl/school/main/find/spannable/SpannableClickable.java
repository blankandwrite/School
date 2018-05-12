package aqtc.gl.school.main.find.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.SchoolApplication;


/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int DEFAULT_COLOR_ID = R.color.color_8290AF;
    /**
     * text颜色
     */
    private int textColor ;

    public SpannableClickable() {
        this.textColor = SchoolApplication.getContext().getResources().getColor(DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int textColor){
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
