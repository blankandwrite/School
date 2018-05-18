package aqtc.gl.school.main.find.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.main.find.activity.FindCircleShareActivity;
import aqtc.gl.school.main.find.enums.FindShareType;
import aqtc.gl.school.widget.popwindow.SelectPicPopWindow;

/**
 * @author gl
 * @date 2018/5/16
 * @desc 发现发布动态弹框选择来源
 */
public class FindSendPopupUtil {

    public static void goSelectPic(final Activity context) {
        final List<String> mStringList = new ArrayList<>();
        mStringList.add("拍照");
        mStringList.add("从相册中选取");
        final SelectPicPopWindow selectPicPopWindow = new SelectPicPopWindow(context, mStringList);
        if (!selectPicPopWindow.isShowing()) {
            selectPicPopWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        selectPicPopWindow.setOnIetmSelectListener(new SelectPicPopWindow.OnIetmSelectListener() {
            @Override
            public void select(int position) {
                Intent intent = new Intent(context, FindCircleShareActivity.class);
                intent.putExtra(FindCircleShareActivity.SHARE_TYPE, FindShareType.JUMP);
                switch (position) {
                    case 0:
                        intent.putExtra(FindCircleShareActivity.JUMP_TYPE, FindCircleShareActivity.CAMERA);
                        selectPicPopWindow.dismiss();
                        break;
                    case 1:
                        intent.putExtra(FindCircleShareActivity.JUMP_TYPE, FindCircleShareActivity.PIC_SELECTOR);
                        selectPicPopWindow.dismiss();
                        break;
                }
                context.startActivity(intent);
            }
        });
    }

    public static void jumpSelectPic(final Activity context, final JumpListener listener) {
        final List<String> mStringList = new ArrayList<>();
        mStringList.add("拍照");
        mStringList.add("从相册中选取");
        final SelectPicPopWindow selectPicPopWindow = new SelectPicPopWindow(context, mStringList);
        if (!selectPicPopWindow.isShowing()) {
            selectPicPopWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }

        selectPicPopWindow.setOnIetmSelectListener(new SelectPicPopWindow.OnIetmSelectListener() {
            @Override
            public void select(int position) {
                switch (position) {
                    case 0:
                  //      listener.doJump(FindShareType.JUMP, FindCircleShareActivity.CAMERA);
                        selectPicPopWindow.dismiss();
                        break;
                    case 1:
                        listener.doJump(FindShareType.JUMP, FindCircleShareActivity.PIC_SELECTOR);
                        selectPicPopWindow.dismiss();
                        break;
                }
            }
        });

    }

    public static interface JumpListener {
        void doJump(FindShareType shareType, int jumpType);
    }

}
