package aqtc.gl.school.main.find.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import aqtc.gl.school.R;
import aqtc.gl.school.main.eventbus.find.NewMessageEvent;
import aqtc.gl.school.utils.ShapeDrawableFactory;
import aqtc.gl.school.utils.image.ImageLoad;
import aqtc.gl.school.utils.sharepreference.PreferenceHelper;

/**
 * @author gl
 * @date 2018/6/12
 * @desc 消息展示
 */

public class FindNewMessageLayout extends LinearLayout {

    private ImageView headImg;
    private TextView contentText;

    public FindNewMessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable bgDrawable = ShapeDrawableFactory.makeRectDrawable(context, R.drawable.img_default, R.drawable.img_default, 5);
        setBackgroundDrawable(bgDrawable);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headImg = (ImageView) findViewById(R.id.headImg);
        contentText = (TextView) findViewById(R.id.contentText);
        refresh(new NewMessageEvent());
    }

    /**
     * 事件订阅者自定义的接收方法
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(NewMessageEvent event) {
        String msg = PreferenceHelper.getInstance("find").getString(getContext(), "new_msg", "");
        try {
            if (TextUtils.isEmpty(msg)) {
                setVisibility(View.GONE);
            } else {
                setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject(msg);
                String url = jsonObject.getString("headImage");
                int text = jsonObject.getInt("count");
                ImageLoad.loadRoundImage(getContext(),"",R.mipmap.ic_user,headImg);
                contentText.setText(text + "条新消息");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void saveMessage(Context context, String msg) {
        PreferenceHelper.getInstance("find").save(context, "new_msg", msg);
    }

    public static void postRefresh() {
        EventBus.getDefault().post(new NewMessageEvent());
    }

    public static void clearMessage(Context context) {
        PreferenceHelper.getInstance("find").clear(context);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}
