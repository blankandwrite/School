package aqtc.gl.school.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

import aqtc.gl.school.common.Global;
import aqtc.gl.school.utils.file.FileUtils;

/**
 * @author gl
 * @date 2018/5/14
 * @desc 获得本地照片工具类
 */
public class LocalGetPicUtil {
    public static final int REQUEST_CODE_LOCAL = 0x2;
    public static final int REQUEST_CODE_CAMERA = 0x4;
    public Object a;
    public Context context;
    public File mCurrentPhotoFile;

    public LocalGetPicUtil(Context context, Object a) {
        this.context=context;
        this.a = a;
    }

    /**
     * 从图库获取图片
     */
    public void selectPic() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        if(a instanceof Activity){
            ((Activity)a).startActivityForResult(intent, REQUEST_CODE_LOCAL);
        }else if(a instanceof Fragment){
            ((Fragment)a).startActivityForResult(intent, REQUEST_CODE_LOCAL);
        }

    }

    /**
     * 照相获取图片
     */
    public void selectPicFromCamera() {
        if (!Utils.isExitsSdcard()) {
            ToastUtils.showMsg(context, "SD卡不存在");
            return;
        }
        String picPath = Global.getPicPath(context).getDirPath();
        String fileName = FileUtils.getFileName() + "jpg";
        mCurrentPhotoFile = new File(picPath + fileName);
        mCurrentPhotoFile.getParentFile().mkdirs();

        if(a instanceof Activity){
            ((Activity)a).startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mCurrentPhotoFile)), REQUEST_CODE_CAMERA);
        }else if(a instanceof Fragment){
            ((Fragment)a).startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mCurrentPhotoFile)), REQUEST_CODE_CAMERA);
        }

    }
}
