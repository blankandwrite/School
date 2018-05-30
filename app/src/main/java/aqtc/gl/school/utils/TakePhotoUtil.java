package aqtc.gl.school.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author gl
 * @date 2018/5/30
 * @desc
 */
public class TakePhotoUtil {

    public static void takePhoto(Activity activity,int code){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/school/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        //改变Uri  aqtc.gl.school.fileprovider 注意和manifest中的一致
        Uri uri = FileProvider.getUriForFile(activity, "aqtc.gl.school.fileprovider", file);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, code);
    }
}
