package aqtc.gl.school.main.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.main.find.enums.FindShareType;
import aqtc.gl.school.main.find.utils.FindSendPopupUtil;
import aqtc.gl.school.main.find.widgets.videolist.widget.TextureVideoView;
import aqtc.gl.school.utils.ShapeDrawableFactory;
import aqtc.gl.school.utils.ToastUtils;
import aqtc.gl.school.utils.file.MimeTypeMap;
import aqtc.gl.school.utils.file.media.PhotoFolderEntity;
import aqtc.gl.school.utils.image.ImageLoad;
import aqtc.gl.school.widget.dialog.LoadingDialog;
import aqtc.gl.school.widget.flowlayout.FlowLayout;
import aqtc.gl.school.widget.flowlayout.wrapper.TagAdapter;
import aqtc.gl.school.widget.flowlayout.wrapper.TagFlowLayout;

import static aqtc.gl.school.main.common.FindCameraType.CAMERA_MODE;
import static aqtc.gl.school.main.common.FindCameraType.MODE_NONE;
import static aqtc.gl.school.main.common.FindCameraType.TYPE_IMG;
import static aqtc.gl.school.main.common.FindCameraType.TYPE_URL;
import static aqtc.gl.school.main.common.FindCameraType.TYPE_VIDEO;


/**
 * @author gl
 * @date 2018/5/16
 * @desc 发布动态
 */
public class FindCircleShareActivity extends BaseActivity {
    public static final String SHARE_CONTENT = "SHARE_CONTENT";
    public static final String SHARE_TYPE = "SHARE_TYPE";
    public static final String JUMP_TYPE = "JUMP_TYPE";
    public static final int CAMERA = 0x11;
    public static final int PIC_SELECTOR = 0x22;
    public static final int DELETE = 0x33;

    private static final int MAX_COUNT = Global.FIND_PIC_SELECT_MAX_COUNT;
    private static final String ADD_DEFAULT = "";

    private Handler mHandler = new Handler();
    protected LoadingDialog dialog;
    private TextView sendBtn;
    private ViewStub viewStub;
    private Runnable finishCallback = new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
            finish();
        }
    };

    private EditText sendContent;
    private TagFlowLayout tagFlowLayout;
    private TagAdapter adapter;
    private ArrayList<String> contents = new ArrayList<>();
    private int hasSelectCount;
    private FindShareType type;
    private int mJumpType;
    private int mode = MODE_NONE;

    @Override
    public int getActivityViewById() {
        return R.layout.activity_find_circle_share;
    }

    @Override
    public void initView() {
        resolveIntent();
        if (type == null || type.isNone()) {
            finish();
            return;
        }
        sendContent = (EditText) findViewById(R.id.sendContent);
        viewStub = (ViewStub) findViewById(R.id.viewStub);
        if (type.isJump()) {
            jumpToSelect();
            viewStub.setLayoutResource(R.layout.view_flowlayout_tags);
            viewStub.inflate();
            initFlowLayout();
        } else if (type.isTextOnly()) {//仅仅发送文本
            mode = TYPE_IMG;
        } else {
            mode = TYPE_URL;
            viewStub.setLayoutResource(R.layout.find_viewstub_urlbody);
            viewStub.inflate();
            processShareLink();
        }
        sendBtn = (TextView) findViewById(R.id.sendBtn);
        Drawable bgDrawable = ShapeDrawableFactory.makeRectDrawable(this, R.color.blue3, R.color.blue3, 1);
        sendBtn.setBackgroundDrawable(bgDrawable);
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sendContent.getText().toString())) {
                    ToastUtils.showMsg(mContext, "请输入这一刻想法");
                    return;
                }
                if (mode == MODE_NONE)
                    return;
                if (mode == TYPE_URL) {
                    doShare(sendContent.getText().toString());
                } else {
                    contents.remove("");
                    //视频模式下必须发送视频
                    if (mode == TYPE_VIDEO && contents.size() == 0) {
                        return;
                    }
                    dialog.show();
              //      newItem = makeCircleItem();
                  //  newItem = uploadExecutor.upload(context, makeCircleItem());
                    mHandler.postDelayed(finishCallback, 8500);
                }
            }
        });
        dialog = new LoadingDialog(mContext);
        dialog.setContent("发送中...");
    }

    @Override
    public void findTitleViewId() {
       mTitleView = findViewById(R.id.titleView);
       mTitleView.setTitle("");
       mTitleView.setRightTitle("发布");
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void resolveIntent() {
        Intent intent = getIntent();
        type = (FindShareType) intent.getSerializableExtra(SHARE_TYPE);
        type = resolveShareIntent();
        mJumpType = intent.getIntExtra(JUMP_TYPE, -1);
    }

    protected FindShareType resolveShareIntent() {
        return type;
    }

    protected void processShareLink() {

    }


    private void jumpToSelect() {
       Intent intent = new Intent();
        if (mJumpType == CAMERA) {
            intent.setClass(this, VideoRecorderActivity.class);
            intent.putExtra(CAMERA_MODE, mode);
           startActivityForResult(intent, mJumpType);
        } else if (mJumpType == PIC_SELECTOR) {
            if (getMode(contents) == TYPE_IMG) { //如果上一次是图片进入选择只能选择图片，如果全部删除掉或者一个都没选，那就可以选择图片或视频//
                PhotoSelectActivity.openSelect(FindCircleShareActivity.this, mJumpType, MAX_COUNT - hasSelectCount, PhotoFolderEntity.FileType.IMAGE);
            } else {
                //图片最大选择数量
                intent.setClass(this, PhotoSelectActivity.class);
                intent.putExtra(CAMERA_MODE, mode);
                intent.putExtra(PhotoSelectActivity.SELECT_TOTAL, MAX_COUNT - hasSelectCount);
                PhotoSelectActivity.openSelect(FindCircleShareActivity.this, mJumpType, MAX_COUNT - hasSelectCount);
            }
        }

    }

    private void initFlowLayout() {
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.tagFlowLayout);
        adapter = new ContentPreViewAdapter(contents);
        tagFlowLayout.setAdapter(adapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String data = contents.get(position);
                if (TextUtils.isEmpty(data)) {//点击添加图片
                    FindSendPopupUtil.jumpSelectPic(FindCircleShareActivity.this, new FindSendPopupUtil.JumpListener() {
                        @Override
                        public void doJump(FindShareType shareType, int jumpType) {
                            resetMode();
                            type = shareType;
                            mJumpType = jumpType;
                            jumpToSelect();
                        }
                    });
                } else {
                    if (MimeTypeMap.isVideo(data)) {
                        SimpleVideoPlayActivity.openVideo(mContext, data, data);
                    } else {
                        //预览图片
                        ArrayList datas = (ArrayList) contents.clone();
                        datas.remove("");
                        //imagesize是作为loading时的图片size
                        ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                        ImagePagerActivity.startImagePagerActivity(mContext,datas,position,imageSize);
                    }
                }
                return false;
            }
        });
    }

    private void resetMode() {
        ArrayList<String> datas = (ArrayList<String>) contents.clone();
        datas.remove("");
        if (datas == null || datas.isEmpty()) {
            mode = MODE_NONE;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != Activity.RESULT_OK) {
            if (mode == MODE_NONE) {
                finish();
            }
            return;
        }
        ArrayList<String> datas = null;
        switch (requestCode) {
            case CAMERA:
            case PIC_SELECTOR:
                datas = data.getStringArrayListExtra(PhotoSelectActivity.SELECT_DATA);
                if (datas != null && datas.size() > 0) {
                    contents.addAll(datas);
                }
                break;
            case DELETE:
                datas = data.getStringArrayListExtra(PhotoSelectActivity.SELECT_DATA);
                contents.clear();
                if (datas != null && datas.size() > 0) {
                    contents.addAll(datas);
                }
                break;
        }
        contents.remove(ADD_DEFAULT);
        hasSelectCount = contents.size();
        //确认数据类型
        mode = getMode(contents);
        if (contents.size() < MAX_COUNT && (TYPE_IMG == mode)) {
            contents.add(ADD_DEFAULT);
        }
        adapter.notifyDataChanged();
    }

    private int getMode(List<String> datas) {
        //确认数据类型
        if (datas.size() > 0) {
            return (mode = MimeTypeMap.isImage(datas.get(0)) ? TYPE_IMG : TYPE_VIDEO);
        }
        return mode;
    }

    private class ContentPreViewAdapter extends TagAdapter<String> {

        public ContentPreViewAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, final int position, final String data) {
            View v = getLayoutInflater().inflate(R.layout.find_circle_share_preview_item, null, false);
            ImageView previewImg = (ImageView) v.findViewById(R.id.previewImg);
            FrameLayout videoBody = (FrameLayout) v.findViewById(R.id.videoBody);
            ImageView ivDelete = v.findViewById(R.id.iv_delete);
            final TextureVideoView videoView = (TextureVideoView) v.findViewById(R.id.textureVideoView);
            ImageView videoFrame = (ImageView) v.findViewById(R.id.videoFrame);
            videoView.mute();
            if (!TextUtils.isEmpty(data)) {
                ivDelete.setVisibility(View.VISIBLE);
                if (MimeTypeMap.isVideo(data)) {
                    previewImg.setVisibility(View.GONE);
                    videoBody.setVisibility(View.VISIBLE);
                    ImageLoad.getVideoImageGlide(mContext, data).centerCrop().into(videoFrame);
                    videoView.setVideoPath(data);
                    videoView.start();
                    videoView.setMediaPlayerCallback(new TextureVideoView.MediaPlayerCallback() {

                        @Override
                        public void onPrepared(MediaPlayer mp) {

                        }

                        @Override
                        public void onStoped(MediaPlayer mp) {

                        }

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            videoView.start();
                        }

                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {

                        }

                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        }

                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            return false;
                        }

                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            return false;
                        }
                    });
                } else {
                    previewImg.setVisibility(View.VISIBLE);
                    videoBody.setVisibility(View.GONE);
                    ImageLoad.getImageGlide(mContext, data,Global.IMAGE_DEFAULT).centerCrop().into(previewImg);
                }
            }else {
                ivDelete.setVisibility(View.GONE);
            }
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contents.remove(data);
                    notifyDataChanged();
                }
            });
            return v;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void doShare(String content) {

    }
}
