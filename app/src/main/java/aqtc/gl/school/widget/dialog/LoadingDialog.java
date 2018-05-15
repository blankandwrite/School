package aqtc.gl.school.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import aqtc.gl.school.R;


/**
 * @author gl
 * @date 2018/5/14
 * @desc 自定义loading
 */
public class LoadingDialog extends Dialog {

    private ImageView progressView;
    private TextView contentText;
 //   private Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading);

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog_style);
        setContentView(R.layout.dialog_layout_loading);
        progressView = (ImageView) findViewById(R.id.progressView);
        contentText = (TextView) findViewById(R.id.contentText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContent(String content) {
        contentText.setText(content);
    }

    @Override
    public void show() {
   //     progressView.startAnimation(loadAnimation);
        super.show();
    }

    public void showCancelalbe(boolean flag) {
        setCancelable(flag);
        setCanceledOnTouchOutside(flag);
        show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
        //    progressView.clearAnimation();
            super.dismiss();
        }
    }
}
