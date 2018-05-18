package aqtc.gl.school.main.find.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.main.find.adapter.CommonAdapter;
import aqtc.gl.school.main.find.widgets.popup.PhotoFolderPopWindow;
import aqtc.gl.school.utils.ToastUtils;
import aqtc.gl.school.utils.Utils;
import aqtc.gl.school.utils.file.MimeTypeMap;
import aqtc.gl.school.utils.file.media.PhotoFolderEntity;
import aqtc.gl.school.utils.file.media.SdcardFileInfo;
import aqtc.gl.school.utils.file.media.SdcardFileQueryUtil;
import aqtc.gl.school.widget.TitleView;

import static aqtc.gl.school.main.common.FindCameraType.CAMERA_MODE;
import static aqtc.gl.school.main.common.FindCameraType.MODE_NONE;
import static aqtc.gl.school.main.common.FindCameraType.TYPE_VIDEO;


/**
 * @author gl
 * @date 2018/5/18
 * @desc 图片选择
 */
public class PhotoSelectActivity extends BaseActivity implements
        PhotoFolderPopWindow.OnImageDirSelected, OnItemClickListener {

    public static final String SELECT_TOTAL = "select_total";
    public static final String SELECT_DATA = "select_data";
    public static final String SELECT_MODE = "select_mode";

    private static final int REQUEST_TAKE_PHOTO_CODE = 1001;
    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    private ArrayList<String> mSelectedImage = new ArrayList<String>();
    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<PhotoFolderEntity> mImageFolders = new ArrayList<PhotoFolderEntity>();
    private PhotoFolderEntity mCurrentFolderEntity;

    private GridView mGirdView;
    private CommonAdapter<String> mAdapter;
    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;

    private PhotoFolderPopWindow mListImageDirPopupWindow;

    private int mSelectTotalCount = 1;
    private int mode;
    private PhotoFolderEntity.FileType showFileType = PhotoFolderEntity.FileType.ALL;
    private Uri mCameraPhotoName;

    private Set<ImageView> mCacheView = new HashSet<ImageView>();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.obj != null)
                mImageFolders = (List<PhotoFolderEntity>) msg.obj;
            if (mImageFolders != null && mImageFolders.size() > 0) {
                selectFolder(mImageFolders.get(0));
            }// 为View绑定数据
            initListDirPopupWindow();// 初始化展示文件夹的popupWindow
            return true;
        }
    });

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mSelectTotalCount = bundle.getInt(SELECT_TOTAL, 1);
                showFileType = (PhotoFolderEntity.FileType) bundle.getSerializable(SELECT_MODE);
            }
        }
        mode = intent.getIntExtra(CAMERA_MODE, MODE_NONE);

    }

    public static void openSelect(Activity context, int requestCode, int maxCount, PhotoFolderEntity.FileType fileType) {
        Intent intent = new Intent(context, PhotoSelectActivity.class);
        Bundle bundle = new Bundle();
        if (fileType != null) {
            bundle.putSerializable(PhotoSelectActivity.SELECT_MODE, fileType);
        }
        intent.putExtras(bundle);
        intent.putExtra(SELECT_TOTAL, maxCount);
        context.startActivityForResult(intent, requestCode);
    }

    public static void openSelect(Activity context, int requestCode, int maxCount) {
        openSelect(context, requestCode, maxCount, null);
    }

    @Override
    public int getActivityViewById() {
        return R.layout.find_activity_select_photo;
    }

    @Override
    public void initView() {
        getData();
        if (mImageFolders == null || mImageFolders.size() == 0)
            getImages();
        mGirdView = (GridView) findViewById(R.id.photo_select_gridview);
        mAdapter = new CommonAdapter<String>(this, null,
                R.layout.find_photo_select_grid_item) {
            Drawable defaultDrawable = getResources().getDrawable(
                    R.drawable.img_default);// 取得一个默认Drawable以提升加载速度

            @Override
            public void convert(final ViewHolder helper, final String item) {
                ImageView mImageView = helper.getView(R.id.photo_grid_item_img);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
                params.width = Utils.getScreenWidth(mContext)/4-24;
                params.height = Utils.getScreenWidth(mContext)/4-24;
                mImageView.setLayoutParams(params);
                mImageView.setImageDrawable(defaultDrawable);
                ImageView smallVideo = helper.getView(R.id.smallVideoImg);
                ImageView mSelect = helper.getView(R.id.photo_grid_item_select);
                setImageByUrl(mImageView, item);
                if (mCacheView == null)
                    mCacheView = new HashSet<ImageView>();
                if (!mCacheView.contains(mImageView)) {
                    mCacheView.add(mImageView);
                }
                if (MimeTypeMap.isVideo(item)) {
                    mSelect.setVisibility(View.GONE);
                    smallVideo.setVisibility(View.VISIBLE);
                } else {
                    mSelect.setVisibility(View.VISIBLE);
                    smallVideo.setVisibility(View.GONE);
                }
                if (mSelectedImage.contains(item)) {
                    mSelect.setImageResource(R.drawable.icon_selected);
                    mImageView.setColorFilter(Color.parseColor("#77000000"));
                } else {
                    mSelect.setImageResource(R.mipmap.icon_no_selected);
                    mImageView.setColorFilter(null);
                }

                mSelect.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage(helper, item);
                    }
                });

                mImageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImage(helper, item);
                    }
                });
            }
        };
        mGirdView.setAdapter(mAdapter);
        mGirdView.setOnItemClickListener(this);
        mChooseDir = (TextView) findViewById(R.id.photo_select_dir);
        mImageCount = (TextView) findViewById(R.id.photo_dir_total_count);
        mBottomLy = (RelativeLayout) findViewById(R.id.photo_select_dir_layout);
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow
                        .setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        updateTitle();
        mTitleView.setRightTitle("完成");
        mTitleView.setRightTitleListener(new TitleView.RightTitleListener() {
            @Override
            public void rightTitleClick() {
                goBack();
            }
        });
    }

    private void updateTitle() {
        mTitleView.setTitle("图片选择(" + mSelectedImage.size() + "/" + mSelectTotalCount + ")");

    }

    /**
     * 返回上一页面，并设置返回结果
     */
    private void goBack() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra(SELECT_DATA, mSelectedImage);
        int size = mSelectedImage.size();
        if (size > 0) {//不替换出现文件找不到错误
            for (int i = 0; i < size; i++) {
                mSelectedImage.set(i, mSelectedImage.get(i).replace("file:///", "/"));
            }
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 初始化展示文件夹的popupWindow
     */
    private void initListDirPopupWindow() {
        mListImageDirPopupWindow = new PhotoFolderPopWindow(
                LayoutParams.MATCH_PARENT,
                (int) (Utils.getScreenWidth(this) * 0.7), mImageFolders,
                LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.find_photo_select_list, null));
        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // 设置背景颜色正常
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    public void selectFolder(PhotoFolderEntity folder) {
        mCurrentFolderEntity = folder;
        if (folder.getType().isAll()) {
            mAdapter.updateList(folder.getAllList(mImageFolders));
        } else if (folder.getType().isAllVideo()) {
            mAdapter.updateList(folder.getVideoList());
        } else {
            if (folder.isAllImage()) {
                mAdapter.updateList(folder.getListImage(mImageFolders));
            } else {
                mAdapter.updateList(folder.getListImage());
            }
        }
        if (mAdapter.getCount() > 0)
            mGirdView.setSelection(0);
        mChooseDir.setText(folder.getName());
        if (mListImageDirPopupWindow != null)
            mListImageDirPopupWindow.dismiss();
    }

    public void selectImage(CommonAdapter.ViewHolder holder, String item) {
        ImageView imageView = holder.getView(R.id.photo_grid_item_img);
        ImageView selectView = holder.getView(R.id.photo_grid_item_select);
        if (mSelectedImage.contains(item)) {
            mSelectedImage.remove(item);
            selectView.setImageResource(R.mipmap.icon_no_selected);
            imageView.setColorFilter(null);
        } else {
            if (mSelectedImage.size() < mSelectTotalCount) {
                mSelectedImage.add(item);
                selectView.setImageResource(R.drawable.icon_selected);
                imageView.setColorFilter(Color.parseColor("#77000000"));
            } else {
                ToastUtils.showMsg(this, "选择数量已达上限");
            }
        }
        if (null != mTitleView){
            updateTitle();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        /*if (mCurrentFolderEntity.isAllImage() && position == 0) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mCameraPhotoName = Uri.fromFile(FileUtils
                    .getFilePath(this, System.currentTimeMillis() + ".jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraPhotoName);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);
            return;
        }*/
        /*Intent intent = new Intent(this, PhotoViewGalleryActivity.class);
        ArrayList<String> list = (ArrayList<String>) mAdapter.getDataList();
		if (mCurrentFolderEntity.isAllImage()) {
			list = new ArrayList<String>(list);
			list.remove(0);// 去除相机按钮
			position -= 1;
		}
		intent.putStringArrayListExtra(
				PhotoViewGalleryActivity.TAG_COVER_ENTITY_LIST, list);
		intent.putExtra(PhotoViewGalleryActivity.TAG_COVER_ENTITY_INDEX,
				position);
		startActivity(intent);*/

        String data = mAdapter.getItem(position);
        if (MimeTypeMap.isVideo(data)) {
            if (mode == MODE_NONE || mode == TYPE_VIDEO) {
                mSelectedImage.clear();
                mSelectedImage.add(data);
                goBack();
            } else {
                ToastUtils.showMsg(this, "只能选择图片");
            }
        }
    }

    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            ToastUtils.showMsg(this, "暂无外部存储");
            return;
        }
        queryImages(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        Log.d("ayke", "resultCode=" + requestCode);
        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PHOTO_CODE) {
            if (mCameraPhotoName != null) {
                mSelectedImage.clear();
                mSelectedImage.add(mCameraPhotoName.toString());
                goBack();
            }
        }
        mCameraPhotoName = null;
    }

    @Override
    public void finish() {
        super.finish();
        if (mCacheView != null) {
            Iterator<ImageView> iterator = mCacheView.iterator();
            while (iterator.hasNext()) {
                ImageView view = iterator.next();
                view.setImageDrawable(null);
            }
            mCacheView.clear();
            mCacheView = null;
        }
        mHandler = null;
        System.gc();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描
     */
    private void queryImages(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> images = SdcardFileQueryUtil.queryAllImages(context);
                List<SdcardFileInfo> videos = SdcardFileQueryUtil.queryAllVideo(context);
                List<PhotoFolderEntity> videoFolders = SdcardFileQueryUtil.makeVideos(context, videos);
                List<PhotoFolderEntity> imageFolders = SdcardFileQueryUtil.makeImages(context, images);
                PhotoFolderEntity mImgDir = new PhotoFolderEntity();
                if (showFileType != null && showFileType.isImage()) {
                    mImgDir.setType(PhotoFolderEntity.FileType.IMAGE);
                    mImgDir.setDir("/所有图片");
                    mImgDir.setAllImage(true);
                    mImgDir.setCount(imageFolders);
                    if (imageFolders.size() > 0)
                        mImgDir.setFirstImagePath(imageFolders.get(0)
                                .getFirstImagePath());
                } else {
                    imageFolders.addAll(0, videoFolders);
                    mImgDir.setCount(imageFolders);
                    mImgDir.setType(PhotoFolderEntity.FileType.ALL);
                    mImgDir.setDir("/所有图片和视频");
                    if (imageFolders.size() > 0)
                        mImgDir.setFirstImagePath(imageFolders.get(0)
                                .getFirstImagePath());
                }

               /* PhotoFolderEntity mVideoDir = new PhotoFolderEntity();
                mVideoDir.setType(PhotoFolderEntity.FileType.ALL_VIDEO);
                mVideoDir.setDir("/所有视频");
                if (videoFolders.size() > 0)
                    mVideoDir.setFirstImagePath(videoFolders.get(0)
                            .getFirstImagePath());
                imageFolders.add(0, mVideoDir);*/
                imageFolders.add(0, mImgDir);
                // 通知Handler扫描图片完成
                if (mHandler != null) {
                    mHandler.obtainMessage(0x110, imageFolders).sendToTarget();
                }
            }
        }).start();
    }
}
