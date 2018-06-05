package aqtc.gl.school.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.find.activity.PhotoSelectActivity;
import aqtc.gl.school.main.home.activity.MyInfoEditActivity;
import aqtc.gl.school.utils.LogX;
import aqtc.gl.school.utils.TakePhotoUtil;
import aqtc.gl.school.utils.ToastUtils;
import aqtc.gl.school.utils.file.FileUtils;
import aqtc.gl.school.utils.file.media.PhotoFolderEntity;
import aqtc.gl.school.utils.image.ImageLoad;
import aqtc.gl.school.widget.popwindow.SelectPicPopWindow;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * @author gl
 * @date 2018/5/26
 * @desc
 */
public class MyFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.iv_photo)
    ImageView headPhoto;

    private Uri mFileUri;
    private SelectPicPopWindow mSelectPicPopWindow;
    private List<String> mStringList = new ArrayList<>();
    private boolean isPission;

    public static MyFragment getInstance() {
        return new MyFragment();
    }

    @Override
    public void initView(View rootView) {
        initPermission();
        mStringList.add("拍照");
        mStringList.add("从相册中选取");
        if (null == mSelectPicPopWindow) {
            mSelectPicPopWindow = new SelectPicPopWindow(mContext, mStringList);
        }
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
        if (!isPission){
            ToastUtils.showMsg(mContext,"请开启相关权限");
            return;
        }
        if (!mSelectPicPopWindow.isShowing()) {
            mSelectPicPopWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }
        mSelectPicPopWindow.setOnIetmSelectListener(new SelectPicPopWindow.OnIetmSelectListener() {
            @Override
            public void select(int position) {
                switch (position) {
                    case 0:
                        mFileUri = TakePhotoUtil.takePhoto(MyFragment.this, Global.PHOTO_REQUEST_CAREMA);
                        break;
                    case 1:
                        PhotoSelectActivity.openSelect(MyFragment.this, Global.SELECT_PHOTO, 1, PhotoFolderEntity.FileType.IMAGE);
                        break;
                }
                mSelectPicPopWindow.dismiss();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Global.PHOTO_REQUEST_CAREMA:
                if (resultCode == RESULT_OK) {
                    Uri destination = Uri.fromFile(new File(Global.getPicPath(mContext).getDirPath() + FileUtils.getFileName() + "jpg"));
                    Crop.of(mFileUri, destination).asSquare().start(mContext, MyFragment.this);
                }
                break;
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);
                break;
            case Global.SELECT_PHOTO:
                ArrayList<String> datas = data.getStringArrayListExtra(PhotoSelectActivity.SELECT_DATA);
                if (datas != null && datas.size() > 0) {
                    Uri destination = Uri.fromFile(new File(Global.getPicPath(mContext).getDirPath() + FileUtils.getFileName() + "jpg"));
                    String path = datas.get(0);
                    mFileUri = Uri.fromFile(new File(path));
                    Crop.of(mFileUri, destination).asSquare().start(mContext, MyFragment.this);
                }
                break;
        }
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            LogX.e("RESULT_OK", Crop.getOutput(result).getPath());
            //    upLoad(new File(Crop.getOutput(result).getPath()));
            ImageLoad.loadRoundImage(mContext, Crop.getOutput(result).getPath(), R.mipmap.ic_user, headPhoto);
        } else if (resultCode == Crop.RESULT_ERROR) {
            ToastUtils.showMsg(mContext, Crop.getError(result).getMessage());
        }
    }

    private void initPermission() {
        String[] perms = {Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (EasyPermissions.hasPermissions(mContext, perms)) {
            // 已有权限
            isPission = true;
        } else {
            // 没有权限 申请
            EasyPermissions.requestPermissions(this, "因为功能需要，需要使用相关权限，请允许",
                    Global.PERMSSION_CODE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //允许申请的权限
        isPission = true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //拒绝权限
        isPission=false;
        ToastUtils.showMsg(mContext, "您拒绝了相关权限，可能会导致相关功能不可用");
    }


}
