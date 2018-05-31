package aqtc.gl.school.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

import aqtc.gl.school.common.Global;
import aqtc.gl.school.utils.file.FileUtils;

/**
 * @author gl
 * @date 2018/5/30
 * @desc
 */
public class TakePhotoUtil {

    public static Uri  takePhoto(Activity activity,int code){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Global.getPicPath(activity).getDirPath()+ FileUtils.getFileName()+"jpg");
        file.getParentFile().mkdirs();
        Uri mFileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //改变Uri  aqtc.gl.school.fileprovider 注意和manifest中的一致
            mFileUri = FileProvider.getUriForFile(activity, "aqtc.gl.school.fileprovider", file);
        } else {
            mFileUri = Uri.fromFile(file);
        }
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        activity.startActivityForResult(intent, code);
        return mFileUri;
    }


    public static Uri takePhoto(Fragment fragment, int code){
        File file = new File(Global.getPicPath(fragment.getActivity()).getDirPath()+ FileUtils.getFileName()+"jpg");
        file.getParentFile().mkdirs();
        Uri mFileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //改变Uri  aqtc.gl.school.fileprovider 注意和manifest中的一致
            mFileUri = FileProvider.getUriForFile(fragment.getActivity(), "aqtc.gl.school.fileprovider", file);
        } else {
            mFileUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
        fragment.startActivityForResult(intent, code);
        return mFileUri;
    }


    /**
     * 开启系统裁剪相片
     */
    public static void startPhotoCrop(Activity activity,Uri fileUri) {
        //创建file文件，用于存储剪裁后的照片
        File cropImage =  new File(Global.getPicPath(activity).getDirPath()+ FileUtils.getFileName()+"jpg");

        try {
            if (cropImage.exists()) {
                cropImage.delete();
            }
            cropImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri cropImgUri = Uri.fromFile(cropImage);
        Intent intent = new Intent("com.android.camera.action.CROP");

        //需要加上这两句话  ： uri 权限 兼容7.0
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        //设置源地址uri
        intent.setDataAndType(fileUri, "image/*");
        intent.putExtra("crop", "true");
        if (android.os.Build.MODEL.contains("HUAWEI")) {//华为特殊处理 不然会显示圆
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        } else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        //设置目的地址uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImgUri);
        //设置图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);//data不需要返回,避免图片太大异常
        intent.putExtra("noFaceDetection", true); // no face detection
        activity.startActivityForResult(intent, 103);
    }
}
