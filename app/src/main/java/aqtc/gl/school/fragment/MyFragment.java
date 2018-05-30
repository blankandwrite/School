package aqtc.gl.school.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.library.log.LogX;

import java.io.File;
import java.io.IOException;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.home.activity.MyInfoEditActivity;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


/**
 * @author gl
 * @date 2018/5/26
 * @desc
 */
public class MyFragment extends BaseFragment {


    private Uri mFileUri;


    public static MyFragment getInstance() {
        return new MyFragment();
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.tv_name)
    void name() {
        MyInfoEditActivity.goMyInfoEditActivity(mContext, 0);
    }

    @OnClick(R.id.tv_academy)
    void academy() {
        MyInfoEditActivity.goMyInfoEditActivity(mContext, 1);
    }

    @OnClick(R.id.tv_jibie)
    void jibie() {
        MyInfoEditActivity.goMyInfoEditActivity(mContext, 2);
    }

    @OnClick(R.id.tv_zhuanye)
    void zhuanye() {
        MyInfoEditActivity.goMyInfoEditActivity(mContext, 3);
    }

    @OnClick(R.id.tv_class)
    void clzz() {
        MyInfoEditActivity.goMyInfoEditActivity(mContext, 4);
    }

    @OnClick(R.id.iv_photo)
    void changePhoto() {
        try {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //创建File对象，用于存储拍照后的照片
            File outputImage=new File(mContext.getExternalCacheDir(),"out_image.jpg");//SD卡的应用关联缓存目录
            if(outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //改变Uri  aqtc.gl.school.fileprovider 注意和manifest中的一致
                mFileUri = FileProvider.getUriForFile(mContext, "aqtc.gl.school.fileprovider", outputImage);
            } else {
                mFileUri = Uri.fromFile(outputImage);
            }
            //启动相机程序
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, Global.PHOTO_REQUEST_CAREMA);
        }catch (Exception e){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Global.PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    LogX.e("PHOTO_REQUEST_CAREMA", "CROP");
                    startPhotoCrop();
                }
                break;
            case Global.CROP_PHOTO:
                if (resultCode == RESULT_OK) {

                }
                break;
        }
    }

    /**
     * 开启裁剪相片
     */
    public void startPhotoCrop() {
        //创建file文件，用于存储剪裁后的照片
       File cropImage =  new File(mContext.getExternalCacheDir(),"crop_image.jpg");

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
        intent.setDataAndType(mFileUri, "image/*");
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
        startActivityForResult(intent, Global.CROP_PHOTO);
    }
}
